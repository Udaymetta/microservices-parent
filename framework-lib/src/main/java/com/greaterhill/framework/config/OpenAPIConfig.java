package com.greaterhill.framework.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenAPIConfig {

    @Value("${spring.application.name}")
    private String app;

    @Autowired
    private Environment environment;

    @Bean
    public OpenAPI myOpenAPI() {
        Server localServer = new Server();
        String port = environment.getProperty("local.server.port") != null ? environment.getProperty("local.server.port") : environment.getProperty("server.port");
        localServer.setUrl("http://localhost:" + port);
        localServer.setDescription("Server URL in Local environment");

        Info info = new Info()
                .title(app)
                .version("1.0")
                .description("This Service exposes API endpoints to manage " + app + " Application");

        return new OpenAPI().info(info).servers(List.of(localServer));
    }
}