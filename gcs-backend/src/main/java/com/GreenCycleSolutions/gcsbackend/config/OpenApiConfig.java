package com.GreenCycleSolutions.gcsbackend.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().addSecurityItem(new SecurityRequirement().
                        addList("Basic Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Basic Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().name("gsc")
                .type(SecurityScheme.Type.HTTP)
                .scheme("basic")
                .in(SecurityScheme.In.HEADER);
    }
}
