package com.cuscatlan.payments.infrastructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for setting up Swagger documentation for the API.
 * This class provides the OpenAPI specification for the Cuscatlan Payment Service.
 */
@Configuration
public class SwaggerConfig {

    /**
     * Creates and configures an OpenAPI bean with API metadata.
     *
     * @return an OpenAPI instance containing information about the API
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Cuscatlan Payment Service API")
                        .version("1.0")
                        .description("Microservice for managing payments"));
    }
}
