package com.ysmjjsy.goya.component.doc.configuration;

import com.google.common.collect.ImmutableList;
import com.ysmjjsy.goya.component.common.constants.GoyaConstants;
import com.ysmjjsy.goya.component.core.configuration.CoreAutoConfiguration;
import com.ysmjjsy.goya.component.core.context.ServiceContextHolder;
import com.ysmjjsy.goya.component.doc.configuration.properties.SwaggerProperties;
import com.ysmjjsy.goya.component.doc.server.OpenApiServerResolver;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.*;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p></p>
 *
 * @author goya
 * @since 2025/6/13 18:00
 */
@Slf4j
@AutoConfiguration(after = CoreAutoConfiguration.class)
@EnableConfigurationProperties(SwaggerProperties.class)
@SecuritySchemes({
        @SecurityScheme(name = GoyaConstants.OPEN_API_SECURITY_SCHEME_BEARER_NAME, type = SecuritySchemeType.OAUTH2, bearerFormat = "JWT", scheme = "bearer",
                flows = @OAuthFlows(
                        password = @OAuthFlow(authorizationUrl = "${goya.platform.endpoint.authorization-uri}", tokenUrl = "${goya.platform.endpoint.access-token-uri}", refreshUrl = "${goya.platform.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all")),
                        clientCredentials = @OAuthFlow(authorizationUrl = "${goya.platform.endpoint.authorization-uri}", tokenUrl = "${goya.platform.endpoint.access-token-uri}", refreshUrl = "${goya.platform.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all"))
//                        authorizationCode = @OAuthFlow(authorizationUrl = "${goya.platform.endpoint.user-authorization-uri}", tokenUrl = "${goya.platform.endpoint.access-token-uri}", refreshUrl = "${goya.platform.endpoint.access-token-uri}", scopes = @OAuthScope(name = "all"))
                )),
})
public class SpringdocAutoConfiguration {

    @PostConstruct
    public void postConstruct() {
        log.debug("[Goya] |- component [doc] SpringdocAutoConfiguration auto configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenApiServerResolver openApiServerResolver() {
        OpenApiServerResolver resolver = () -> {
            Server server = new Server();
            server.setUrl(ServiceContextHolder.getInstance().getUrl());
            return ImmutableList.of(server);
        };
        log.trace("[Goya] |- component [doc] |- bean [openApiServerResolver] register.");
        return resolver;
    }

    @Bean
    @ConditionalOnMissingBean
    public OpenAPI createOpenApi(OpenApiServerResolver openApiServerResolver) {
        OpenAPI openApi = new OpenAPI()
                .servers(openApiServerResolver.getServers())
                .info(new Info().title(GoyaConstants.SYSTEM_NAME)
                        .description("Goya Cloud Microservices Architecture")
                        .version("Swagger V3")
                        .license(new License().name("AGPL 3.0").url("https://www.gnu.org/licenses/agpl-3.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Goya Cloud Documentation")
                        .url(GoyaConstants.WEBSITE));
        log.trace("[Goya] |- component [doc] |- bean [OpenAPI] register.");
        return openApi;
    }

}
