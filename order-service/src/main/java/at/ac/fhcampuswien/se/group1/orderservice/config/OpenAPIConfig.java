package at.ac.fhcampuswien.se.group1.orderservice.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI apiInfo() {
        final String securitySchemeName = "bearerAuth";

        final Server server = new Server();
        server.setUrl("http://localhost:8084/");
        
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
                                .title("Order Service")
                                .description(
                                        "This is the order service for the Car Rental microservice architecture. " +
                                                "For further " +
                                                "information, please visit our [Wiki](https://se-2022.atlassian" +
                                                ".net/wiki/)")
                                .version("1.0.0")
                                .license(new License().name("MIT")
                                        .url("https://github.com/caf3babe/car-rental-microservices/blob/main/LICENSE"))
                );
    }
}
