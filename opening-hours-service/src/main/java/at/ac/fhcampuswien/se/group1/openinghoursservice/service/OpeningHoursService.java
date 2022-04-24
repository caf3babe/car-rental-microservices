package at.ac.fhcampuswien.se.group1.openinghoursservice.service;

import at.ac.fhcampuswien.se.group1.openinghoursservice.event.LocationFinishedEvent;
import at.ac.fhcampuswien.se.group1.openinghoursservice.event.LocationRejectedEvent;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.Location;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHoursNotFoundException;
import at.ac.fhcampuswien.se.group1.openinghoursservice.repository.OpeningHoursRepository;
import at.ac.fhcampuswien.se.group1.openinghoursservice.utility.TransactionId;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Log4j2
@Service
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;

    private final ApplicationEventPublisher publisher;

    private final TransactionId transactionId;

    public OpeningHoursService(OpeningHoursRepository openingHoursRepository, ApplicationEventPublisher publisher, TransactionId transactionId) {
        this.openingHoursRepository = openingHoursRepository;
        this.publisher = publisher;
        this.transactionId = transactionId;
    }

    public List<OpeningHours> getAllOpeningHours() {
        List<OpeningHours> openingHours = new ArrayList<>();
        openingHoursRepository.findAll().iterator().forEachRemaining(openingHours::add);
        return openingHours;
    }

    public OpeningHours getOpeningHoursById(Integer id) {
        return openingHoursRepository.findById(id).orElseThrow(() -> new OpeningHoursNotFoundException("Opening Hours was not found for " + id));
    }

    private void publishLocationFinished(Location location) {

        LocationFinishedEvent event = new LocationFinishedEvent(transactionId.getTransactionId(), location);

        log.debug("Publishing location finished event {}", event);

        publisher.publishEvent(event);

    }

    private void publishRejectedLocation(Location location) {

        LocationRejectedEvent event = new LocationRejectedEvent(transactionId.getTransactionId(), location);

        log.debug("Publishing rejected location event {}", event);

        publisher.publishEvent(event);

    }

    public void checkLocation(Location location) {

        log.debug("Confirm for location id {}", location.getLocationId());


        if (
                openingHoursRepository.findAll()
                        .stream()
                        .anyMatch(openingHours -> openingHours.getOpeningHoursId().equals(location.getOpeningHours().getOpeningHoursId()))
        ) {

            log.debug("Location confirmed for location id {}", location.getLocationId());

            publishLocationFinished(location);

            return;

        } else {
            publishRejectedLocation(location);
        }
    }


}
