package at.ac.fhcampuswien.se.group1.carservice.repository;

import at.ac.fhcampuswien.se.group1.carservice.model.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface CarRepository extends MongoRepository<Car, BigInteger> {
}
