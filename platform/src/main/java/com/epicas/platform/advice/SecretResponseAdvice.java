package com.epicas.platform.advice;

import com.epicas.platform.constants.EncryptConstant;
import com.epicas.platform.constants.RequestHeaderConstant;
import com.epicas.platform.exception.SecrectException;
import com.epicas.platform.result.Result;
import com.epicas.platform.result.SecretResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * 响应数据加密
 * @author liuyang
 * @date 2023年10月16日 09:38
 */
@ControllerAdvice
public class SecretResponseAdvice implements ResponseBodyAdvice {

    @Value("${platform.privateKey}")
    private String privateKey;

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof Result) {
            try {
                Result result = (Result) body;
                if (result.getData() != null) {
                    //1.如果存在data，则需要对data进行加密
                    //1.1从请求头中获取distributionNo
                    String distributorNo = request.getHeaders().getFirst(RequestHeaderConstant.DISTRIBUTOR_NO);
                    //1.2 生成当前时间戳
                    long time = System.currentTimeMillis();
                    //1.3 基于 distributorNo、time、privateKey 生成临时密钥 tempKey
                    String tempKey = DigestUtils.md5DigestAsHex((privateKey + distributorNo + time).getBytes());
                    //1.4 对data进行加密
                    //1.4.1 获取data的json字符串
                    String dataJson = new ObjectMapper().writeValueAsString(result.getData());
                    //1.4.2 对data的json字符串进行加密
                    //初始化 AES 加密器
                    Cipher cipher = Cipher.getInstance(EncryptConstant.ALGORITHM_MODE);
                    SecretKeySpec secretKey = new SecretKeySpec(tempKey.getBytes(), EncryptConstant.ALGORITHM);
                    cipher.init(Cipher.ENCRYPT_MODE, secretKey);
                    //加密
                    byte[] secretResult = cipher.doFinal(dataJson.getBytes(EncryptConstant.CHARSET));
                    result.setData(new String(secretResult));
                    //1.4.3 生成签名sign
                    String sign = DigestUtils.md5DigestAsHex((tempKey + time + result.getData()).getBytes());
                    return new SecretResult(time,sign,result);
                }
            } catch (Exception e) {
                throw new SecrectException();
            }
        }
        return body;
    }
}
