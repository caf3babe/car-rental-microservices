package at.ac.fhcampuswien.se.group1.core.repository;

import at.ac.fhcampuswien.se.group1.core.models.Car;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarRepository extends MongoRepository<Car, Integer> {
}
