package at.ac.fhcampuswien.se.group1.orderservice.service;

import at.ac.fhcampuswien.se.group1.carservice.domain.dto.CarRequest;
import at.ac.fhcampuswien.se.group1.carservice.domain.exception.CarNotFoundException;
import at.ac.fhcampuswien.se.group1.carservice.domain.mapper.CarMapper;
import at.ac.fhcampuswien.se.group1.carservice.model.Car;
import at.ac.fhcampuswien.se.group1.carservice.model.CarStatus;
import at.ac.fhcampuswien.se.group1.carservice.model.CurrencySymbol;
import at.ac.fhcampuswien.se.group1.carservice.repository.CarRepository;
import at.ac.fhcampuswien.se.group1.carservice.service.CarService;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class) class CarServiceTest {
    
    @InjectMocks CarService carService;
    @Mock CarRepository carRepository;
    @Mock CarMapper carMapper;
    
    Car savedCar;
    
    @BeforeEach
    void setUp() {
        savedCar = new Car(
                new BigInteger("30452260786781021453601130959"),
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
        );
    }
    
    @AfterEach
    void tearDown() {
    }
    
    @Test
    void createCar() {
        
        CarRequest carCreateRequest = new CarRequest(
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
        );
        
        Car mappedCar = new Car(
                new BigInteger("30452260786781021453601130959"),
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
        );
        
        Mockito.when(carMapper.create(Mockito.any())).thenReturn(mappedCar);
        Mockito.when(carRepository.save(Mockito.any())).thenReturn(savedCar);
        
        assertEquals(this.carService.createCar(carCreateRequest), savedCar);
    }
    
    @Test
    void getCarById() {
        Mockito.when(carRepository.findById(new BigInteger("30452260786781021453601130959")))
                .thenReturn(Optional.ofNullable(savedCar));
        assertEquals(carService.getCarById(new BigInteger("30452260786781021453601130959")), savedCar);
        
    }
    
    @Test
    void getCarById_throwsExceptionWhenNotExists() {
        BigInteger id = new BigInteger("30452260786781021453601130959");
        String exceptionMessage = String.format("Car with id %s could not be found", id);
        
        Mockito.when(carRepository.findById(Mockito.any())).thenThrow(
                new CarNotFoundException(exceptionMessage)
        );
        
        Assert.assertThrows(CarNotFoundException.class, () -> carService.getCarById(id));
    }
    
    @Test
    void getCarsByStatus() {
        // TODO implement test
        assertTrue(true);
    }
    
    @Test
    void getCars() {
        // TODO implement test
        assertTrue(true);
    }
    
    @Test
    void updateCarById() {
        // TODO implement test
        assertTrue(true);
    }
}