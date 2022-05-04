package at.ac.fhcampuswien.se.group1.carservice.utility;

import at.ac.fhcampuswien.se.group1.carservice.model.Car;
import at.ac.fhcampuswien.se.group1.carservice.model.CarStatus;
import at.ac.fhcampuswien.se.group1.carservice.model.CurrencySymbol;
import at.ac.fhcampuswien.se.group1.carservice.repository.CarRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@Component
public class SeedDatabase implements CommandLineRunner {
    
    private CarRepository carRepository;
    
    public SeedDatabase(CarRepository carRepository) {
        this.carRepository = carRepository;
    }
    
    @Override
    public void run(String... args) {
        
        this.carRepository.deleteAll();
        
        this.carRepository.save(new Car(
                BigInteger.valueOf(1),
                CarStatus.AVAILABLE,
                "W0L000041T212345678890",
                "Audi",
                LocalDate.now().minusYears(10),
                "Black",
                "TT",
                "Coupé",
                "Diesel",
                BigDecimal.ONE,
                300,
                "Hybrid",
                "automatic",
                false,
                2,
                65000D,
                CurrencySymbol.USD,
                "https://www.auto-data.net/en/audi-tt-rs-roadster-8s-facelift-2019-generation-7105#image8"
        ));
        
        this.carRepository.save(new Car(
                BigInteger.valueOf(0),
                CarStatus.AVAILABLE,
                "V999992039482309480",
                "Alfa Romeo",
                LocalDate.now().minusYears(10),
                "Black",
                "159 Ti",
                "Kombi",
                "Diesel",
                BigDecimal.TEN,
                150,
                "combustion",
                "manual",
                false,
                5,
                9800D,
                CurrencySymbol.USD,
                "https://www.auto-data.net/images/f56/Alfa-Romeo-159-Sportwagon.jpg"
        ));
    }
}
