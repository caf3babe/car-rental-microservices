package at.ac.fhcampuswien.se.group1.orderservice.event.handler;

import at.ac.fhcampuswien.se.group1.orderservice.event.CarAvailableEvent;
import at.ac.fhcampuswien.se.group1.orderservice.event.CarUnavailableEvent;
import at.ac.fhcampuswien.se.group1.orderservice.event.LocationExistentEvent;
import at.ac.fhcampuswien.se.group1.orderservice.event.LocationNonexistentEvent;
import at.ac.fhcampuswien.se.group1.orderservice.service.OrderService;
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
public class RabbitEventHandler {
    
    private final OrderService orderService;
    
    private ObjectMapper mapper;
    
    @RabbitListener(queues = {"${queue.car-unavailable}"})
    public void handleCarUnavailableEvent(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling a car unavailable event {}", payload);
        
        CarUnavailableEvent event = mapper.readValue(payload, CarUnavailableEvent.class);
        
        
        orderService.updateOrderCarUnavailable(event);
        
    }
    
    @RabbitListener(queues = {"${queue.car-available}"})
    public void handleCarAvailableEvent(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling a car available event {}", payload);
        
        CarAvailableEvent event = mapper.readValue(payload, CarAvailableEvent.class);
        
        orderService.updateOrderCarAvailable(event);
        
    }
    
    @RabbitListener(queues = {"${queue.location-nonexistent}"})
    public void handleLocationNonexistentEvent(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling a location nonexistent event {}", payload);
        
        LocationNonexistentEvent event = mapper.readValue(payload, LocationNonexistentEvent.class);
        
        orderService.updateOrderLocationNonexistent(event);
        
    }
    
    @RabbitListener(queues = {"${queue.location-existent}"})
    public void handleLocationExistentEvent(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling a location existent event {}", payload);
        
        LocationExistentEvent event = mapper.readValue(payload, LocationExistentEvent.class);
        
        orderService.updateOrderLocationExistent(event);
        
    }
}
