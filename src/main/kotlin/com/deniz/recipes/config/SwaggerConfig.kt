package com.deniz.recipes.config

import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.OpenAPI
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

// SwaggerConfig is config to have customized info.
@Configuration
class SwaggerConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Buy Recipes API")
                    .version("1.0")
                    .description("API for purchasing recipe products")
            )
    }
}