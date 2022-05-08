package at.ac.fhcampuswien.se.group1.currencyserviceapplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class CurrencyServiceApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(CurrencyServiceApplication.class, args);
    }
    
}
