package at.ac.fhcampuswien.se.group1.repositories;

import at.ac.fhcampuswien.se.group1.models.Location;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends CrudRepository<Location, Integer>{
}
