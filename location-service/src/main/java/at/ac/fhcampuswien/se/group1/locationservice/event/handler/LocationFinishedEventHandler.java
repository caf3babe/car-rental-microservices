package at.ac.fhcampuswien.se.group1.locationservice.event.handler;

import at.ac.fhcampuswien.se.group1.locationservice.event.LocationFinishedEvent;
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
public class LocationFinishedEventHandler {

    private final LocationService locationService;

    private ObjectMapper mapper;

    @RabbitListener(queues = {"${queue.location-finished}"})
    public void handleLocationDoneEvent(@Payload String payload) throws JsonProcessingException {

        log.info("Handling a location finished event {}", payload);

        LocationFinishedEvent event = mapper.readValue(payload, LocationFinishedEvent.class);

        locationService.updateLocationAsFinished(event.getLocation());

    }
}
