package at.ac.fhcampuswien.se.group1.locationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class LocationServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(LocationServiceApplication.class, args);
        
    }
    
}
