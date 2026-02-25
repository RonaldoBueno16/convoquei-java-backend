package br.com.convoquei.backend._shared.api.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes(
                        "bearerAuth",
                        new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Convoquei API")
                        .version("1.0")
                        .description("""
                                API para gerenciamento de eventos e convocações.

                                ## Respostas de Erro

                                Todos os endpoints desta API retornam erros no formato [Problem Details (RFC 9457)](https://www.rfc-editor.org/rfc/rfc9457), com o `Content-Type: application/problem+json`.

                                | Status | Situação |
                                |--------|----------|
                                | `400`  | Dados inválidos na requisição (validação, JSON malformado, parâmetro ausente) |
                                | `401`  | Não autenticado — token ausente, inválido ou expirado |
                                | `403`  | Sem permissão para executar a operação |
                                | `404`  | Recurso não encontrado |
                                | `409`  | Conflito — recurso já existe |
                                | `422`  | Regra de negócio violada |
                                | `500`  | Erro interno inesperado |

                                **Exemplo de corpo de erro:**
                                ```json
                                {
                                  "type": "urn:convoquei:problem:validation",
                                  "title": "Requisição inválida",
                                  "status": 400,
                                  "detail": "Erro de validação.",
                                  "instance": "/api/v1/organizations/",
                                  "timestamp": "2026-02-25T00:00:00Z",
                                  "errors": {
                                    "name": "não deve estar em branco"
                                  }
                                }
                                ```
                                """));
    }
}
