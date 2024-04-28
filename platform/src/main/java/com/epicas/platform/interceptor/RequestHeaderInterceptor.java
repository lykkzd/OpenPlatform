package com.epicas.platform.interceptor;

import com.epicas.platform.constants.RequestHeaderConstant;
import com.epicas.platform.holder.ClientIpHolder;
import com.epicas.platform.holder.OrgIdHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 拦截请求保存orgId
 * @author liuyang
 * @date 2023年10月07日 10:09
 */
@Component
public class RequestHeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 获取请求路径
        String requestUri = request.getRequestURI();

        // 判断请求路径是否与 Swagger 相关
        if (requestUri.contains("/v2/api-docs") || requestUri.contains("/swagger-resources")
                || requestUri.contains("/swagger-ui.html") || requestUri.contains("/webjars")
                || requestUri.contains("/doc.html")
        ) {
            // 如果是 Swagger 相关的请求，不进行拦截，返回 true
            return true;
        }
        Long orgId = Long.valueOf(request.getHeader(RequestHeaderConstant.ORG_ID));
        String clientIp = request.getHeader(RequestHeaderConstant.CLIENT_IP);
        if (orgId != null) {
            OrgIdHolder.setOrgId(orgId);
        }
        if (clientIp != null) {
            ClientIpHolder.setClientIp(clientIp);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        OrgIdHolder.clear();  // 清除ThreadLocal中的数据，防止内存泄漏
    }

}
