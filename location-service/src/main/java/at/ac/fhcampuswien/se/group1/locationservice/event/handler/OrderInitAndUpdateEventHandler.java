package at.ac.fhcampuswien.se.group1.locationservice.event.handler;

import at.ac.fhcampuswien.se.group1.locationservice.domain.exception.LocationNotFoundException;
import at.ac.fhcampuswien.se.group1.locationservice.event.OrderInitEvent;
import at.ac.fhcampuswien.se.group1.locationservice.service.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@AllArgsConstructor
@Component
public class OrderInitAndUpdateEventHandler {
    
    private final LocationService locationService;
    
    private ObjectMapper mapper;
    
    @RabbitListener(queues = {"${queue.order-init}", "${queue.order-update}"})
    public void handleOrderInitAndUpdate(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling an order init or update event {}", payload);
        
        OrderInitEvent event = mapper.readValue(payload, OrderInitEvent.class);
        
        try {
            locationService.getLocationById(event.getRentalLocationId());
        } catch (LocationNotFoundException exception) {
            // TODO send event back if location nonexistent
        }
        
        try {
            locationService.getLocationById(event.getReturnLocationId());
        } catch (LocationNotFoundException exception) {
            // TODO send event back if location nonexistent
        }
    }
}
