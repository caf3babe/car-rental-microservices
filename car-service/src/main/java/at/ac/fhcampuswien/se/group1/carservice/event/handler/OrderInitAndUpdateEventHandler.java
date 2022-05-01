package at.ac.fhcampuswien.se.group1.carservice.event.handler;

import at.ac.fhcampuswien.se.group1.carservice.domain.exception.CarNotFoundException;
import at.ac.fhcampuswien.se.group1.carservice.event.CarAvailableEvent;
import at.ac.fhcampuswien.se.group1.carservice.event.OrderInitEvent;
import at.ac.fhcampuswien.se.group1.carservice.service.CarService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@AllArgsConstructor
@Component
public class OrderInitAndUpdateEventHandler {
    
    private final CarService carService;
    private final ApplicationEventPublisher publisher;
    private ObjectMapper mapper;
    
    @Transactional
    @RabbitListener(queues = {"${queue.order-init}", "${queue.order-update}"})
    public void handleOrderInitAndUpdate(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling an order init or update event {}", payload);
        
        OrderInitEvent event = mapper.readValue(payload, OrderInitEvent.class);
        
        try {
            // TODO currently only checking if car exists in database but not checking CarState;
            log.info("Publishing message");
            publisher.publishEvent(new CarAvailableEvent(event.getOrderId(), carService.getCarById(event.getCarId())));
            log.info("Message published");
        } catch (CarNotFoundException exception) {
            log.error("An error occured while fetching car with id {}", event.getCarId());
            log.error(exception.getMessage());
            // TODO send event back if car unavailable
        }
    }
}
