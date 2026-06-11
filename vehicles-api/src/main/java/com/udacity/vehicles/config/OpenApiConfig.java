package com.udacity.vehicles.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Vehicles API",
                version = "1.0",
                description = "API for managing vehicles and enriching them with pricing and location data"
        )
)
public class OpenApiConfig {
}
