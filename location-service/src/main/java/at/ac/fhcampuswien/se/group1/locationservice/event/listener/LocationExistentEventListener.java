package at.ac.fhcampuswien.se.group1.locationservice.event.listener;

import at.ac.fhcampuswien.se.group1.locationservice.event.LocationExistentEvent;
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
public class LocationExistentEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final String queueLocationExistent;
    private final ObjectMapper mapper;

    public LocationExistentEventListener(RabbitTemplate rabbitTemplate,
                                 @Value("${queue.location-existent}") String queueLocationExistent, ObjectMapper mapper) {

        this.rabbitTemplate = rabbitTemplate;
        this.queueLocationExistent = queueLocationExistent;
        this.mapper = mapper;

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(LocationExistentEvent event) throws JsonProcessingException {

        log.debug("Sending location exist event to {}, event: {}", queueLocationExistent, event);

        rabbitTemplate.convertAndSend(queueLocationExistent, mapper.writeValueAsString(event));

    }
}
