package at.ac.fhcampuswien.se.group1.orderservice.event;

import at.ac.fhcampuswien.se.group1.orderservice.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@AllArgsConstructor
@Data
public class CarAvailableEvent {
    BigInteger orderId;
    BigInteger carId;
    Car car;
}
