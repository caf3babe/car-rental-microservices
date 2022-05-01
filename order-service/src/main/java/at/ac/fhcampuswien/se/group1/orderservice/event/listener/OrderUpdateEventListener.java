package at.ac.fhcampuswien.se.group1.orderservice.event.listener;

import at.ac.fhcampuswien.se.group1.orderservice.event.OrderInitEvent;
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
public class OrderUpdateEventListener {
    
    private final RabbitTemplate rabbitTemplate;
    private final String queueOrderInit;
    private final ObjectMapper mapper;
    
    public OrderUpdateEventListener(RabbitTemplate rabbitTemplate,
                                    @Value("${queue.order-update}") String queueOrderInit, ObjectMapper mapper) {
        
        this.rabbitTemplate = rabbitTemplate;
        this.queueOrderInit = queueOrderInit;
        this.mapper = mapper;
        
    }
    
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(OrderInitEvent event) throws JsonProcessingException {
        
        log.debug("Sending order update event to {}, event: {}", queueOrderInit, event);
        
        rabbitTemplate.convertAndSend(queueOrderInit, mapper.writeValueAsString(event));
        
    }
}
