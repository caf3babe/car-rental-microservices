package at.ac.fhcampuswien.se.group1.openinghoursservice.event.listener;

import at.ac.fhcampuswien.se.group1.openinghoursservice.event.LocationRejectedEvent;
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
public class RejectedEventListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final String queueLocationRejected;
    private final ObjectMapper mapper;
    
    public RejectedEventListener(RabbitTemplate rabbitTemplate,
                                 @Value("${queue.location-rejected}") String queueLocationRejected,
                                 ObjectMapper mapper) {
        
        this.rabbitTemplate = rabbitTemplate;
        this.queueLocationRejected = queueLocationRejected;
        this.mapper = mapper;
        
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(LocationRejectedEvent event) throws JsonProcessingException {
        
        log.debug("Sending location rejected event to {}, event: {}", queueLocationRejected, event);
        
        rabbitTemplate.convertAndSend(queueLocationRejected, mapper.writeValueAsString(event));
        
    }
}
