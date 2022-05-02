package at.ac.fhcampuswien.se.group1.locationservice.event.handler;

import at.ac.fhcampuswien.se.group1.locationservice.event.CarAvailableEvent;
import at.ac.fhcampuswien.se.group1.locationservice.service.LocationService;
import at.ac.fhcampuswien.se.group1.locationservice.utility.TransactionIdentifier;
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
public class CarAvailableEventHandler {
    
    private final LocationService locationService;
    private ObjectMapper mapper;
    private final TransactionIdentifier transactionId;
    
    @RabbitListener(queues = {"${queue.car-available}"})
    public void handleCarAvailable(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling an car available event {}", payload);
        
        CarAvailableEvent event = mapper.readValue(payload, CarAvailableEvent.class);

        transactionId.setId(event.getTransactionId());

        locationService.checkLocation(event.getOrder());
    }
}
