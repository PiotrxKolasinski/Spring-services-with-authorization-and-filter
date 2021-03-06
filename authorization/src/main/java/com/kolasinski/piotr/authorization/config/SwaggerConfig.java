package com.kolasinski.piotr.authorization.config;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${custom.filter.host}")
    private String filterHost;

    @Bean
    public Docket authorization() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("authorization")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKeyWithAccessToken(), apiKeyWithRefreshToken()));
    }

    @Bean
    public Docket filter() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("filter")
                .host(filterHost)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKeyWithAccessToken(), apiKeyWithRefreshToken()));
    }

    private ApiKey apiKeyWithAccessToken() {
        return new ApiKey("x-auth-access-token", "access_token_key", "header");
    }

    private ApiKey apiKeyWithRefreshToken() {
        return new ApiKey("x-auth-refresh-token", "refresh_token_key", "header");
    }
}
