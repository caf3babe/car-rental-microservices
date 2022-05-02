package at.ac.fhcampuswien.se.group1.locationservice.event;

import at.ac.fhcampuswien.se.group1.locationservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LocationNonexistentEvent {
    private String transactionId;
    private Order order;
}
