package at.ac.fhcampuswien.se.group1.orderservice.event;

import at.ac.fhcampuswien.se.group1.orderservice.model.Order;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CarUnavailableEvent {
    private String transactionId;

    private Order order;
}
