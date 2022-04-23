package at.ac.fhcampuswien.se.group1.locationservice.event.handler;

import at.ac.fhcampuswien.se.group1.locationservice.event.LocationRejectedEvent;
import at.ac.fhcampuswien.se.group1.locationservice.service.LocationService;
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
public class LocationRejectedEventHandler {

    private final LocationService locationService;

    private ObjectMapper mapper;

    @RabbitListener(queues = {"${queue.location-rejected}"})
    public void onOrderCanceled(@Payload String payload) throws JsonProcessingException {

        log.debug("Handling a reject location event {}", payload);

        LocationRejectedEvent event = mapper.readValue(payload, LocationRejectedEvent.class);

        locationService.rejectOrder(event.getLocation().getLocationId());
    }
}
