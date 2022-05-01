package at.ac.fhcampuswien.se.group1.carservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@AllArgsConstructor
@Data
public class CarUnavailableEvent {
    BigInteger orderId;
    BigInteger carId;
}
