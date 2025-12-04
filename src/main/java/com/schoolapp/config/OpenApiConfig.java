package com.schoolapp.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("School Management API")
                        .description("A comprehensive RESTful API for managing school operations including students, teachers, classes, and attendance tracking")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("School App Team")
                                .email("support@schoolapp.com")
                                .url("https://github.com/theCoderFromHell/school-app"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Development server"),
                        new Server()
                                .url("https://api.schoolapp.com")
                                .description("Production server")
                ));
    }
}
