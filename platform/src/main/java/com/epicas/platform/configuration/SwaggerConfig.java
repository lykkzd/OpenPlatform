package com.epicas.platform.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;


/**
 * @author liuyang
 * @date 2023年10月13日 17:46
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public ApiInfo apiInfo() {
        Contact contact = new Contact(
                "liuyang", // 作者姓名
                "", // 作者网址
                "17681203223@163.com"); // 作者邮箱
        return new ApiInfoBuilder()
                .title("对接开放平台-接口文档") // 标题
                .description("对接第三方开放平台微服务") // 描述
                .termsOfServiceUrl("") // 跳转连接
                .version("1.0") // 版本
                .license("")
                .licenseUrl("")
                .contact(contact)
                .build();
    }

    /**
     * 展示路径为com.epicas.platform.controller的所有接口
     * @param environment
     * @return
     */
    @Bean
    public Docket docket1(Environment environment) {
        // 设置请求头
        List<Parameter> parameters = new ArrayList<>();
        parameters.add(new ParameterBuilder()
                .name("orgId") // 字段名
                .description("站点id") // 描述
                .modelRef(new ModelRef("Long")) // 数据类型
                .parameterType("header") // 参数类型
                .defaultValue("347") // 默认值：可自己设置
                .hidden(true) // 是否隐藏
                .required(false) // 是否必须
                .build());
        // 设置环境范围
        Profiles profiles = Profiles.of("dev","test");
        // 如果在该环境返回内则返回：true，反之返回 false
        boolean flag = environment.acceptsProfiles(profiles);
        // 创建一个 swagger 的 bean 实例
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(flag) // 是否开启 swagger：true -> 开启，false -> 关闭
                .apiInfo(apiInfo()) // 配置基本信息
                .groupName("platform") // 修改组名为 "platform"
                // 配置接口信息
                .select() // 设置扫描接口
                // 配置如何扫描接口
                .apis(RequestHandlerSelectors
                                //.any() // 扫描全部的接口，默认
                                //.none() // 全部不扫描
                                .basePackage("com.epicas.platform.controller") // 扫描指定包下的接口，最为常用
                                //.withClassAnnotation(RestController.class) // 扫描带有指定注解的类下所有接口
                                //.withMethodAnnotation(PostMapping.class) // 扫描带有只当注解的方法接口

                )
                .paths(PathSelectors
                                .any() // 满足条件的路径，该断言总为true
                                //.none() // 不满足条件的路径，该断言总为false（可用于生成环境屏蔽 swagger）
                                //.ant("/user/**") // 满足字符串表达式路径
                                //.regex("") // 符合正则的路径
                )
                .build()
                 //添加请求头参数
                .globalOperationParameters(parameters);
    }
}
