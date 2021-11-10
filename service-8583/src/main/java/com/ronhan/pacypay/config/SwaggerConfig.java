package com.ronhan.pacypay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author ﻿bing.shen@onerway.com
 * @date 2018/6/12
 * 说明：Swagger 配置
 */
@Configuration
@EnableSwagger2
@Profile("dev")
public class SwaggerConfig {

    /**
     * 首页 API 说明
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("ISO8583")
                .description("API文档")
                .version("1.0.0")
                .build();
    }


    /**
     * swagger api 访问
     * <a href="http://localhost:8080/api/swagger-ui.html">Swagger地址</a>
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // endpoint 包下的所有类，生成接口文档
                .apis(RequestHandlerSelectors.basePackage("com.ronhan.pacypay.endpoint"))
                .paths(PathSelectors.any())
                .build()
                ;
    }

}