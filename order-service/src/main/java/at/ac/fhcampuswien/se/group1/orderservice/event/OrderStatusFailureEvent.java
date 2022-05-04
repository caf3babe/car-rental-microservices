package at.ac.fhcampuswien.se.group1.orderservice.event;

import at.ac.fhcampuswien.se.group1.orderservice.model.Order;
import at.ac.fhcampuswien.se.group1.orderservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderStatusFailureEvent {
    private String transactionId;
    private Order order;
    private OrderStatus orderOldStatus;
}
