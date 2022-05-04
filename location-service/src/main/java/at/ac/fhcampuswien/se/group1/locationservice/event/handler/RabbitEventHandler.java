package at.ac.fhcampuswien.se.group1.locationservice.event.handler;

import at.ac.fhcampuswien.se.group1.locationservice.event.CarAvailableEvent;
import at.ac.fhcampuswien.se.group1.locationservice.event.LocationFinishedEvent;
import at.ac.fhcampuswien.se.group1.locationservice.event.LocationRejectedEvent;
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
public class RabbitEventHandler {

    private final LocationService locationService;
    private ObjectMapper mapper;
    private final TransactionIdentifier transactionId;

    @RabbitListener(queues = {"${queue.location-rejected}"})
    public void handleOrderRejected(@Payload String payload) throws JsonProcessingException {

        log.info("Handling a reject location event {}", payload);

        LocationRejectedEvent event = mapper.readValue(payload, LocationRejectedEvent.class);

        locationService.rejectLocation(event.getLocation());
    }

    @RabbitListener(queues = {"${queue.location-finished}"})
    public void handleLocationDoneEvent(@Payload String payload) throws JsonProcessingException {

        log.info("Handling a location finished event {}", payload);

        LocationFinishedEvent event = mapper.readValue(payload, LocationFinishedEvent.class);

        locationService.updateLocationAsFinished(event.getLocation());

    }

    @RabbitListener(queues = {"${queue.car-available}"})
    public void handleCarAvailable(@Payload String payload) throws JsonProcessingException {

        log.info("Handling an car available event {}", payload);

        CarAvailableEvent event = mapper.readValue(payload, CarAvailableEvent.class);

        transactionId.setId(event.getTransactionId());

        locationService.checkLocation(event.getOrder());
    }
}
