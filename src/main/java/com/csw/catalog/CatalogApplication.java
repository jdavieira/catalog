package com.csw.catalog;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Application;
import lombok.Generated;
import org.eclipse.microprofile.openapi.annotations.Components;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeIn;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.security.*;

@Generated
@OpenAPIDefinition(
        info = @Info(
                title = "Catalog Service API",
                version = "1.0",
                description = "Catalog Service Web Store"
        ),
        security = @SecurityRequirement(name = "oauth2", scopes = {"read, write"}),
        components = @Components(
                securitySchemes = {
                        @SecurityScheme(
                                securitySchemeName = "oauth2",
                                description = "Oauth2 flow",
                                type = SecuritySchemeType.OAUTH2,
                                in = SecuritySchemeIn.HEADER,
                                flows = @OAuthFlows(
                                        clientCredentials = @OAuthFlow(
                                                tokenUrl = "http://localhost:8080/realms/book/protocol/openid-connect/token",
                                                scopes = {
                                                        @OAuthScope(name = "read", description = "read permission"),
                                                        @OAuthScope(name = "write", description = "write permission"),
                                                }
                                        )
                                )
                        )
                }
        )
)
@ApplicationScoped
public class CatalogApplication extends Application {
}
