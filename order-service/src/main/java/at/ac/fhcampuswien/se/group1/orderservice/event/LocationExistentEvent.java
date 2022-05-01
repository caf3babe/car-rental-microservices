package at.ac.fhcampuswien.se.group1.orderservice.event;

import at.ac.fhcampuswien.se.group1.orderservice.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@AllArgsConstructor
@Data
public class LocationExistentEvent {
    BigInteger orderId;
    Location location;
}
