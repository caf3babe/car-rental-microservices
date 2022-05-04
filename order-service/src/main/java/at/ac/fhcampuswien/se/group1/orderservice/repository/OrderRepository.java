package at.ac.fhcampuswien.se.group1.orderservice.repository;

import at.ac.fhcampuswien.se.group1.orderservice.model.Order;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.math.BigInteger;

@Repository
public interface OrderRepository extends MongoRepository<Order, BigInteger> {
}
