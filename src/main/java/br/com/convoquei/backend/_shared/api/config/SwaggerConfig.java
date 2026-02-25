package br.com.convoquei.backend._shared.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        var problemDetailSchema = new ObjectSchema()
                .name("ProblemDetail")
                .addProperty("type", new Schema<String>().type("string").example("urn:convoquei:problem:validation"))
                .addProperty("title", new Schema<String>().type("string").example("Requisição inválida"))
                .addProperty("status", new Schema<Integer>().type("integer").example(400))
                .addProperty("detail", new Schema<String>().type("string").example("Erro de validação."))
                .addProperty("instance", new Schema<String>().type("string").example("/api/v1/organizations/"))
                .addProperty("timestamp", new Schema<String>().type("string").example("2026-02-25T00:00:00Z"));

        return new OpenAPI()
                .components(new Components()
                        .addSchemas("ProblemDetail", problemDetailSchema)
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Convoquei API")
                        .version("1.0")
                        .description("API para gerenciamento de eventos e convocações."));
    }

    @Bean
    public OperationCustomizer globalErrorResponse() {
        var inlineSchema = new ObjectSchema()
                .addProperty("type", new Schema<String>().type("string").example("urn:convoquei:problem:validation"))
                .addProperty("title", new Schema<String>().type("string").example("Requisição inválida"))
                .addProperty("status", new Schema<Integer>().type("integer").example(400))
                .addProperty("detail", new Schema<String>().type("string").example("Erro de validação."))
                .addProperty("instance", new Schema<String>().type("string").example("/api/v1/organizations/"))
                .addProperty("timestamp", new Schema<String>().type("string").example("2026-02-25T00:00:00Z"));

        var content = new Content().addMediaType(
                "application/problem+json",
                new MediaType().schema(inlineSchema)
        );
        var errorResponse = new ApiResponse()
                .description("Erro — retornado no formato Problem Details (RFC 9457)")
                .content(content);

        return (operation, handlerMethod) -> {
            operation.getResponses().addApiResponse("4XX/5XX", errorResponse);
            return operation;
        };
    }
}



