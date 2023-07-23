package ru.kotomore.regioncatalogapi.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Справочник регионов")
                        .version("v0.0.1"));
    }

}
