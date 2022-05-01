package at.ac.fhcampuswien.se.group1.orderservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@AllArgsConstructor
@Data
public class LocationNonexistentEvent {
    BigInteger orderId;
    Integer locationId;
}
