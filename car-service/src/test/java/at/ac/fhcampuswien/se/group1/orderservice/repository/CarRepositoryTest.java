package at.ac.fhcampuswien.se.group1.orderservice.repository;

import at.ac.fhcampuswien.se.group1.carservice.model.Car;
import at.ac.fhcampuswien.se.group1.carservice.model.CarStatus;
import at.ac.fhcampuswien.se.group1.carservice.model.CurrencySymbol;
import at.ac.fhcampuswien.se.group1.carservice.repository.CarRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@DataMongoTest
public class CarRepositoryTest {
    
    @Autowired
    CarRepository carRepository;
    
    Car car;
    
    @Before
    public void setUp() throws Exception {
        car = carRepository.save(new Car(
                BigInteger.valueOf(0),
                CarStatus.RENTED,
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
        
    }
    
    @Test
    public void shouldNotBeEmpty() {
        Assertions.assertThat(carRepository.findAll()).isNotEmpty();
    }
    
    @Test
    public void readEqualsSetUpCar() {
        Assertions.assertThat(carRepository.findAll().stream().findFirst().orElseThrow().equals(car)).isTrue();
    }
}
