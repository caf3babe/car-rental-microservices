package at.ac.fhcampuswien.se.group1.repository;

import at.ac.fhcampuswien.se.group1.models.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigInteger;

public interface CarRepository extends MongoRepository<Car, BigInteger> {
}
