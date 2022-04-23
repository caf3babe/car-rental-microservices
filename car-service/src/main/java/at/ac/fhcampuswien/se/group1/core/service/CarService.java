package at.ac.fhcampuswien.se.group1.core.service;

import at.ac.fhcampuswien.se.group1.core.domain.dto.CarRequest;
import at.ac.fhcampuswien.se.group1.core.domain.exception.CarNotFoundException;
import at.ac.fhcampuswien.se.group1.core.domain.mapper.CarMapper;
import at.ac.fhcampuswien.se.group1.core.models.Car;
import at.ac.fhcampuswien.se.group1.core.models.CarStatus;
import at.ac.fhcampuswien.se.group1.core.models.CurrencySymbol;
import at.ac.fhcampuswien.se.group1.core.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CarService {

    private final CarRepository carRepository;

    //CHECK_DAVID
    //private final CurrencyConverterWrapper currencyConverterWrapper;

    private final CarMapper carMapper;

    public Car createCar(CarRequest carRequest) {
        //CHECK_DAVID
        //Car car = carMapper.create(carRequest);
        //return carRepository.save(car);
        return null;
    }

    public void deleteCarById(Integer id) {
        carRepository.deleteById(id);
    }

    public Car getCarById(Integer id) {
        return carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car with id " + id + " could not be found"));
    }

    public List<Car> getCarsByStatus(CarStatus carStatus) {
        System.out.println(carStatus.getValue());
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().iterator().forEachRemaining(cars::add);

        return cars.stream().filter(car -> car.getCarStatus() == carStatus).toList();
    }

    public List<Car> getCars(CurrencySymbol currencySymbol) {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().iterator().forEachRemaining(cars::add);
        //CHECK_DAVID
        //cars.forEach(car -> {
        //    car.setPrice(currencyConverterWrapper.convert(car.getPrice(), car.getCurrencySymbol(), currencySymbol));
        //    car.setCurrencySymbol(currencySymbol);
        //});
        return cars;
    }

    public Car updateCarById(Integer id, CarRequest carRequest) {
        Car oldCar = carRepository.findById(id).orElseThrow(()-> new CarNotFoundException("Car was not found for "+ id));
        //CHECK_DAVID
        //Car newCar = carMapper.update(carRequest,oldCar);
        //return carRepository.save(newCar);
        return null;
    }
}
