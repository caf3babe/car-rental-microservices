package at.ac.fhcampuswien.se.group1.authenticationservice.repository;

import at.ac.fhcampuswien.se.group1.authenticationservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;
import java.util.Optional;

@Repository
public interface OrderRepository extends MongoRepository<Order, BigInteger> {
    Optional<Order> findByOrderIdAndLastNameAllIgnoreCase(BigInteger uuid, String lastName);

}
