package at.ac.fhcampuswien.se.group1.locationservice.event.listener;

import at.ac.fhcampuswien.se.group1.locationservice.event.LocationCreatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Component
public class LocationEventListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final String queueLocationCreated;
    private final ObjectMapper mapper;
    
    public LocationEventListener(RabbitTemplate rabbitTemplate,
                                 @Value("${queue.location-created}") String queueLocationCreated, ObjectMapper mapper) {
        
        this.rabbitTemplate = rabbitTemplate;
        this.queueLocationCreated = queueLocationCreated;
        this.mapper = mapper;
        
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(LocationCreatedEvent event) throws JsonProcessingException {
        
        log.debug("Sending location created event to {}, event: {}", queueLocationCreated, event);
        
        rabbitTemplate.convertAndSend(queueLocationCreated, mapper.writeValueAsString(event));
        
    }
}
