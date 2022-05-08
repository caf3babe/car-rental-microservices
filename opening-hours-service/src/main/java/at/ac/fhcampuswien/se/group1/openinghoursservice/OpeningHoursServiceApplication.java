package at.ac.fhcampuswien.se.group1.openinghoursservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class OpeningHoursServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(OpeningHoursServiceApplication.class, args);
    }
    
}
