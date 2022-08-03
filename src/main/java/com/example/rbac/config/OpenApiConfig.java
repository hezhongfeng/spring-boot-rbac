package com.example.rbac.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

// 示例配置类
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI initOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("RBAC API").description("OpenAPI").version("v1.0"));
    }
}
