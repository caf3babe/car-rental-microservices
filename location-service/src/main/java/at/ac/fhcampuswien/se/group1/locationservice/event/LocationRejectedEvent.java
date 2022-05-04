package at.ac.fhcampuswien.se.group1.locationservice.event;

import at.ac.fhcampuswien.se.group1.locationservice.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LocationRejectedEvent {
    private String transactionId;
    private Location location;
}
