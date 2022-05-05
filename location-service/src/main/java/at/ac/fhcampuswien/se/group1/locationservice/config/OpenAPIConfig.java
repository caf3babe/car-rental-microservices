package at.ac.fhcampuswien.se.group1.locationservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class OpenAPIConfig {
    
    @Bean
    public OpenAPI apiInfo() {
        final String securitySchemeName = "bearerAuth";

        final Server server = new Server();
        server.setUrl("http://localhost:8082/");
        
        final List<Server> servers = new ArrayList<>();
        servers.add(server);
        
        return new OpenAPI()
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .servers(
                        new ArrayList<>(
                                servers
                        )
                )
                .info(
                        new Info()
                                .title("Location Service")
                                .description(
                                        "This is the location service for the Car Rental microservice architecture. " +
                                                "For further " +
                                                "information, please visit our [Wiki](https://se-2022.atlassian" +
                                                ".net/wiki/)")
                                .version("1.0.0")
                                .license(new License().name("MIT")
                                        .url("https://github.com/caf3babe/car-rental-microservices/blob/main/LICENSE"))
                );
    }
}
