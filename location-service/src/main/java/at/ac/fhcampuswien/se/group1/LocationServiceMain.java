package at.ac.fhcampuswien.se.group1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"at.ac.fhcampuswien.se.group1.domain.**"})
@EntityScan("at.ac.fhcampuswien.se.group1.domain.**")
public class LocationServiceMain {
    public static void main(String[] args) {
        SpringApplication.run(LocationServiceMain.class, args);
    }
}
