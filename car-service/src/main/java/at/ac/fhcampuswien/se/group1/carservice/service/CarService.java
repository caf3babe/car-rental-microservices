package at.ac.fhcampuswien.se.group1.carservice.service;

import at.ac.fhcampuswien.se.group1.carservice.domain.dto.CarRequest;
import at.ac.fhcampuswien.se.group1.carservice.domain.exception.CarNotFoundException;
import at.ac.fhcampuswien.se.group1.carservice.domain.mapper.CarMapper;
import at.ac.fhcampuswien.se.group1.carservice.event.CarAvailableEvent;
import at.ac.fhcampuswien.se.group1.carservice.event.CarUnavailableEvent;
import at.ac.fhcampuswien.se.group1.carservice.event.handler.CurrencyConvertHandler;
import at.ac.fhcampuswien.se.group1.carservice.model.Car;
import at.ac.fhcampuswien.se.group1.carservice.model.CarStatus;
import at.ac.fhcampuswien.se.group1.carservice.model.CurrencySymbol;
import at.ac.fhcampuswien.se.group1.carservice.model.Order;
import at.ac.fhcampuswien.se.group1.carservice.repository.CarRepository;
import at.ac.fhcampuswien.se.group1.carservice.utility.TransactionIdentifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Log4j2 @RequiredArgsConstructor @Service public class CarService {
    
    private final CarRepository carRepository;
    private final CarMapper carMapper;
    private final CurrencyConvertHandler currencyConvertHandler;
    private final ApplicationEventPublisher publisher;
    private final TransactionIdentifier transactionId;
    
    public Car createCar(CarRequest carRequest) {
        Car car = carMapper.create(carRequest);
        log.debug("Service Mapped Car: [{}]", car);
        
        Car savedCar = carRepository.save(car);
        log.debug("Saved Car: [{}]", savedCar);
        
        return savedCar;
    }
    
    public void deleteCarById(BigInteger id) {
        carRepository.deleteById(id);
    }
    
    public Car getCarById(BigInteger id) {
        return carRepository.findById(id)
                .orElseThrow(() -> new CarNotFoundException("Car with id " + id + " could not be found"));
    }
    
    public Car getCarById(BigInteger id, CurrencySymbol currencySymbol) {
        Car car = this.getCarById(id);
        updatePriceIfNecessary(currencySymbol, car);
        return car;
    }
    
    public List<Car> getCarsByStatus(CarStatus carStatus) {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().iterator().forEachRemaining(cars::add);
        return cars.stream().filter(car -> car.getCarStatus() == carStatus).toList();
    }
    
    public List<Car> getCars(CurrencySymbol currencySymbol) {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().iterator().forEachRemaining(cars::add);
        cars.forEach(car -> updatePriceIfNecessary(currencySymbol, car));
        return cars;
    }
    
    private void updatePriceIfNecessary(CurrencySymbol currencySymbol, Car car) {
        if (!car.getCurrencySymbol().equals(currencySymbol)) {
            try {
                log.info("Sending currency convert message to server for car id: {}", car.getCarId());
                Double convertedPrice =
                        this.currencyConvertHandler.convert(car.getCurrencySymbol(), car.getPrice(), currencySymbol);
                car.setCurrencySymbol(currencySymbol);
                car.setPrice(convertedPrice);
            } catch (JsonProcessingException e) {
                log.error("An error occurred while processing a Json: {}", e.getMessage());
            }
        }
    }
    
    
    public Car updateCarById(BigInteger id, CarRequest carRequest) {
        return carRepository.save(carMapper.update(carRequest,
                carRepository.findById(id).orElseThrow(() -> new CarNotFoundException("Car was not found for " + id))));
    }

    public void checkCar(Order order) {

        log.info("Check for car id {}", order.getCar().getCarId());

        if (
                carRepository.findAll()
                        .stream()
                        .anyMatch(car -> car.getCarId()
                                .equals(order.getCar().getCarId()))
        ) {

            order.setCar(carRepository.findById(order.getCar().getCarId()).get());

            publishCarAvailable(order);

        } else {
            publishCarUnavailable(order);
        }
    }

    private void publishCarAvailable(Order order) {

        CarAvailableEvent event = new CarAvailableEvent(transactionId.getTransactionId(), order);

        log.info("Publishing car available event {}", event);

        publisher.publishEvent(event);

    }

    private void publishCarUnavailable(Order order) {

        CarUnavailableEvent event = new CarUnavailableEvent(transactionId.getTransactionId(), order);

        log.info("Publishing car unavailable event {}", event);

        publisher.publishEvent(event);

    }
}



