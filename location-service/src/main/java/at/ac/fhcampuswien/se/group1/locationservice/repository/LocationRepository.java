package at.ac.fhcampuswien.se.group1.locationservice.repository;

import at.ac.fhcampuswien.se.group1.locationservice.model.Location;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends MongoRepository<Location, Integer> {
}
