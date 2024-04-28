package com.epicas.platformgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关的主启动类
 * @author liuyang
 * @date 2023年09月28日 17:19
 */
@SpringBootApplication
@EnableDiscoveryClient
public class PlatformGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(PlatformGatewayApplication.class, args);
    }
}
