package at.ac.fhcampuswien.se.group1.openinghoursservice.repository;

import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHours;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OpeningHoursRepository extends MongoRepository<OpeningHours,Integer> {

}
