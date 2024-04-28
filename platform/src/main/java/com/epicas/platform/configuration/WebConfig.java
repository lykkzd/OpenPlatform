package com.epicas.platform.configuration;

import com.epicas.platform.converter.factory.IntegerToEnumConverterFactory;
import com.epicas.platform.converter.factory.StringToEnumConverterFactory;
import com.epicas.platform.interceptor.RequestHeaderInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册拦截器
 * @author liuyang
 * @date 2023年10月07日 10:16
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final RequestHeaderInterceptor requestHeaderInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestHeaderInterceptor);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToEnumConverterFactory());
        registry.addConverterFactory(new IntegerToEnumConverterFactory());
    }

}
