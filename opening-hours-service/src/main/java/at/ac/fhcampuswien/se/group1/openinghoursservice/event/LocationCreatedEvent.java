package at.ac.fhcampuswien.se.group1.openinghoursservice.event;

import at.ac.fhcampuswien.se.group1.openinghoursservice.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LocationCreatedEvent {
    
    private String transactionId;
    
    private Location location;
}
