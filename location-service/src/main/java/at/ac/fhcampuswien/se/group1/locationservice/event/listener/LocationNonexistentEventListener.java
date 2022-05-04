package at.ac.fhcampuswien.se.group1.locationservice.event.listener;

import at.ac.fhcampuswien.se.group1.locationservice.event.LocationNonexistentEvent;
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
public class LocationNonexistentEventListener {

    private final RabbitTemplate rabbitTemplate;
    private final String queueLocationNonexistent;
    private final ObjectMapper mapper;

    public LocationNonexistentEventListener(RabbitTemplate rabbitTemplate,
                                 @Value("${queue.location-nonexistent}") String queueLocationNonexistent, ObjectMapper mapper) {

        this.rabbitTemplate = rabbitTemplate;
        this.queueLocationNonexistent = queueLocationNonexistent;
        this.mapper = mapper;

    }

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void onCreateEvent(LocationNonexistentEvent event) throws JsonProcessingException {

        log.debug("Sending location nonexistent event to {}, event: {}", queueLocationNonexistent, event);

        rabbitTemplate.convertAndSend(queueLocationNonexistent, mapper.writeValueAsString(event));

    }
}
