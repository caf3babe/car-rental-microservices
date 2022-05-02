package at.ac.fhcampuswien.se.group1.openinghoursservice.event.handler;

import at.ac.fhcampuswien.se.group1.openinghoursservice.event.LocationCreatedEvent;
import at.ac.fhcampuswien.se.group1.openinghoursservice.service.OpeningHoursService;
import at.ac.fhcampuswien.se.group1.openinghoursservice.utility.TransactionIdentifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@AllArgsConstructor
public class LocationCreatedHandler {
    
    private final ObjectMapper mapper;
    private final OpeningHoursService openingHoursService;
    private final TransactionIdentifier transactionIdentifier;
    
    @RabbitListener(queues = {"${queue.location-created}"})
    public void handle(@Payload String payload) throws JsonProcessingException {
        
        log.info("Handling a created location event {}", payload);
        
        LocationCreatedEvent event = mapper.readValue(payload, LocationCreatedEvent.class);
        
        transactionIdentifier.setId(event.getTransactionId());
        
        openingHoursService.checkLocation(event.getLocation());
        
    }
}
