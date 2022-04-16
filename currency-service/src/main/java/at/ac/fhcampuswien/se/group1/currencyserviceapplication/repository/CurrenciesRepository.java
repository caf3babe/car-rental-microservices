package at.ac.fhcampuswien.se.group1.currencyserviceapplication.repository;

import at.ac.fhcampuswien.se.group1.currencyserviceapplication.model.Currencies;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CurrenciesRepository extends MongoRepository<Currencies,String> {
}
