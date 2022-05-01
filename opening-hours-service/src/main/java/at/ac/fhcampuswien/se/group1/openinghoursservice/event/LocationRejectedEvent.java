package at.ac.fhcampuswien.se.group1.openinghoursservice.event;

import at.ac.fhcampuswien.se.group1.openinghoursservice.model.Location;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LocationRejectedEvent {
    
    private String transactionId;
    
    private Location location;
}
