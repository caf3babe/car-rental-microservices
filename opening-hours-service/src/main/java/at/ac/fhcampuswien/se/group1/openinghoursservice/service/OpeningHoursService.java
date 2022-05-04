package at.ac.fhcampuswien.se.group1.openinghoursservice.service;

import at.ac.fhcampuswien.se.group1.openinghoursservice.event.LocationFinishedEvent;
import at.ac.fhcampuswien.se.group1.openinghoursservice.event.LocationRejectedEvent;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.Location;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHoursNotFoundException;
import at.ac.fhcampuswien.se.group1.openinghoursservice.repository.OpeningHoursRepository;
import at.ac.fhcampuswien.se.group1.openinghoursservice.utility.TransactionIdentifier;
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
    
    private final TransactionIdentifier transactionIdentifier;
    
    public OpeningHoursService(OpeningHoursRepository openingHoursRepository, ApplicationEventPublisher publisher,
                               TransactionIdentifier transactionIdentifier) {
        this.openingHoursRepository = openingHoursRepository;
        this.publisher = publisher;
        this.transactionIdentifier = transactionIdentifier;
    }
    
    public List<OpeningHours> getAllOpeningHours() {
        List<OpeningHours> openingHours = new ArrayList<>();
        openingHoursRepository.findAll().iterator().forEachRemaining(openingHours::add);
        return openingHours;
    }
    
    public OpeningHours getOpeningHoursById(Integer id) {
        return openingHoursRepository.findById(id)
                .orElseThrow(() -> new OpeningHoursNotFoundException("Opening Hours was not found for " + id));
    }
    
    public void checkLocation(Location location) {
        
        log.info("Check for location id {}", location.getLocationId());
        
        if (
                openingHoursRepository.findAll()
                        .stream()
                        .anyMatch(openingHours -> openingHours.getOpeningHoursId()
                                .equals(location.getOpeningHours().getOpeningHoursId()))
        ) {

            openingHoursRepository.findById(location.getOpeningHours().getOpeningHoursId()).ifPresent(location::setOpeningHours);

            publishLocationFinished(location);
            
        } else {
            publishRejectedLocation(location);
        }
    }
    
    private void publishLocationFinished(Location location) {
        
        LocationFinishedEvent event = new LocationFinishedEvent(transactionIdentifier.getId(), location);
        
        log.info("Publishing location finished event {}", event);
        
        publisher.publishEvent(event);
        
    }
    
    private void publishRejectedLocation(Location location) {
        
        LocationRejectedEvent event = new LocationRejectedEvent(transactionIdentifier.getId(), location);
        
        log.info("Publishing location rejected event {}", event);
        
        publisher.publishEvent(event);
        
    }
    
}
