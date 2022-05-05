package at.ac.fhcampuswien.se.group1.openinghoursservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class OpenAPIConfig {
    
    @Bean
    public OpenAPI apiInfo() {
        
        final Server server = new Server();
        server.setUrl("http://localhost:8081/");
        
        final List<Server> servers = new ArrayList<>();
        servers.add(server);
        
        return new OpenAPI()
                .servers(
                        new ArrayList<>(
                                servers
                        )
                )
                .info(
                        new Info()
                                .title("Currency Service")
                                .description(
                                        "This is the opening hours service for the Car Rental microservice " +
                                                "architecture. For further " +
                                                "information, please visit our [Wiki](https://se-2022.atlassian" +
                                                ".net/wiki/)")
                                .version("1.0.0")
                                .license(new License().name("MIT")
                                        .url("https://github.com/caf3babe/car-rental-microservices/blob/main/LICENSE"))
                );
    }
}
