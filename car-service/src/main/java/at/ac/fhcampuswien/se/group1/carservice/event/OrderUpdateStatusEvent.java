package at.ac.fhcampuswien.se.group1.carservice.event;

import at.ac.fhcampuswien.se.group1.carservice.model.Order;
import at.ac.fhcampuswien.se.group1.carservice.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class OrderUpdateStatusEvent {
    private String transactionId;
    private Order order;
    private OrderStatus orderOldStatus;
}
