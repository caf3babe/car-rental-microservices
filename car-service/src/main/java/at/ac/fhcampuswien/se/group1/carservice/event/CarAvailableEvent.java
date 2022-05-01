package at.ac.fhcampuswien.se.group1.carservice.event;

import at.ac.fhcampuswien.se.group1.carservice.model.Car;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@AllArgsConstructor
@Data
public class CarAvailableEvent {
    BigInteger orderId;
    Car car;
}
