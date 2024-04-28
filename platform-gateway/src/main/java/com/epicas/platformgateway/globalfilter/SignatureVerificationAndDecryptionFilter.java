package com.epicas.platformgateway.globalfilter;


import com.epicas.platformgateway.constants.EncryptConstant;
import com.epicas.platformgateway.constants.RequestHeaderConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * 全局网关
 * @author liuyang
 * @date 2023年09月28日 17:22
 */
@Component
public class SignatureVerificationAndDecryptionFilter implements GlobalFilter, Ordered {

    @Value("${gateway.privateKey}")
    private String privateKey;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //0. 从请求头中获取orgId
        Long orgId = Long.valueOf(exchange.getRequest().getHeaders().getFirst(RequestHeaderConstant.ORG_ID));
        //0. 获取客户端 IP 地址
        String clientIp = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
        // 1. 获取请求头中的 distributorNo、time、sign
        ServerHttpRequest request = exchange.getRequest();
        String distributorNo = request.getHeaders().getFirst(RequestHeaderConstant.DISTRIBUTOR_NO);
        String time = request.getHeaders().getFirst(RequestHeaderConstant.TIME);
        String sign = request.getHeaders().getFirst(RequestHeaderConstant.SIGN);
        // 2. 校验 distributorNo、time、sign 是否为空
        if (distributorNo == null || time == null || sign == null) {
            return this.onError(exchange, "Missing headers", HttpStatus.BAD_REQUEST);
        }
        //3. 基于 distributorNo、time、privateKey 生成临时密钥 tempKey
        String tempKey = DigestUtils.md5DigestAsHex((privateKey + distributorNo + time).getBytes());
        //4. 对请求体进行解密
        return request.getBody()//获取请求体
                .next()//从请求体的数据流中获取下一个 DataBuffer
                .flatMap(dataBuffer -> { //处理请求体中的数据
                    //获取请求体中的数据
                    byte[] encryptedPostData = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(encryptedPostData);
                    String encryptedPostDataString = new String(encryptedPostData, StandardCharsets.UTF_8);
                    //生成签名
                    String generatedSign = DigestUtils.md5DigestAsHex((encryptedPostDataString + tempKey + time).getBytes());
                    //校验签名
                    if (!generatedSign.equals(sign)) {
                        return this.onError(exchange, "Invalid sign", HttpStatus.UNAUTHORIZED);
                    }
                    try {
                        //初始化 AES 加密器
                        Cipher cipher = Cipher.getInstance(EncryptConstant.ALGORITHM_MODE);
                        SecretKeySpec secretKey = new SecretKeySpec(tempKey.getBytes(), EncryptConstant.ALGORITHM);
                        cipher.init(Cipher.DECRYPT_MODE, secretKey);
                        //解密请求体
                        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedPostDataString));
                        //创建 DataBuffer 对象
                        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(decryptedData);
                        //修改请求体，并继续向下传递
                        Flux<DataBuffer> bufferFlux = Flux.just(buffer);
                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(request) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return bufferFlux;
                            }
                        };
                        //将请求头中的 orgId 传递下去
                        mutatedRequest = mutatedRequest.mutate()
                                .header(RequestHeaderConstant.ORG_ID, String.valueOf(orgId))
                                .header(RequestHeaderConstant.CLIENT_IP, clientIp)
                                .build();
                        return chain.filter(exchange.mutate().request(mutatedRequest).build());
                    } catch (Exception e) {
                        return this.onError(exchange, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
                    }
                });
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        //异常处理
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        DataBuffer buffer = response.bufferFactory().wrap(err.getBytes());
        return response.writeWith(Flux.just(buffer));
    }

    @Override
    public int getOrder() {
        return -1;  // run before other filters
    }
}
