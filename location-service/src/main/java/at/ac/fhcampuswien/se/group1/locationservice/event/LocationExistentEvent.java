package at.ac.fhcampuswien.se.group1.locationservice.event;

import at.ac.fhcampuswien.se.group1.locationservice.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigInteger;

@AllArgsConstructor
@Data
public class LocationExistentEvent {
    BigInteger orderId;
    Location location;
}
