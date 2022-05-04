package at.ac.fhcampuswien.se.group1.currencyserviceapplication.repository;

import at.ac.fhcampuswien.se.group1.currencyserviceapplication.model.Currencies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrenciesRepository extends MongoRepository<Currencies, String> {
}
