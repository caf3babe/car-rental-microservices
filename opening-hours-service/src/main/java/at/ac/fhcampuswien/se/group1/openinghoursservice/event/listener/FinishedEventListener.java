package at.ac.fhcampuswien.se.group1.openinghoursservice.event.listener;

import at.ac.fhcampuswien.se.group1.openinghoursservice.event.LocationFinishedEvent;
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
public class FinishedEventListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final String queueLocationFinished;
    private final ObjectMapper mapper;
    
    public FinishedEventListener(RabbitTemplate rabbitTemplate,
                                 @Value("${queue.location-finished}") String queueLocationFinished,
                                 ObjectMapper mapper) {
        
        this.rabbitTemplate = rabbitTemplate;
        this.queueLocationFinished = queueLocationFinished;
        this.mapper = mapper;
        
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(LocationFinishedEvent event) throws JsonProcessingException {
        
        log.debug("Sending location finished event to {}, event: {}", queueLocationFinished, event);
        
        rabbitTemplate.convertAndSend(queueLocationFinished, mapper.writeValueAsString(event));
        
    }
}
