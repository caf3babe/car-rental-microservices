package at.ac.fhcampuswien.se.group1.locationservice.service;

import at.ac.fhcampuswien.se.group1.locationservice.domain.dto.CreateLocationRequest;
import at.ac.fhcampuswien.se.group1.locationservice.domain.dto.UpdateLocationRequest;
import at.ac.fhcampuswien.se.group1.locationservice.domain.exception.LocationNotFoundException;
import at.ac.fhcampuswien.se.group1.locationservice.domain.mapper.LocationMapper;
import at.ac.fhcampuswien.se.group1.locationservice.event.LocationCreatedEvent;
import at.ac.fhcampuswien.se.group1.locationservice.model.Location;
import at.ac.fhcampuswien.se.group1.locationservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.locationservice.model.SagaStatus;
import at.ac.fhcampuswien.se.group1.locationservice.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@RequiredArgsConstructor
@Service
public class LocationService {
    
    private final LocationRepository locationRepository;
    
    private final LocationMapper locationMapper;
    
    private final ApplicationEventPublisher publisher;
    
    private final SequenceGeneratorService sequenceGeneratorService;
    
    @Transactional
    public Location createLocation(CreateLocationRequest locationRequest) {
        Location location = locationMapper.create(locationRequest);
        location.setLocationId(sequenceGeneratorService.generateSequence(Location.SEQUENCE_NAME));
        location.setStatus(SagaStatus.CREATED);
        OpeningHours openingHours = new OpeningHours(locationRequest.getOpeningHoursId());
        location.setOpeningHours(openingHours);
        
        log.info("Saving an location {}", location);
        
        Location returnLocation = locationRepository.save(location);
        
        publish(returnLocation);
        
        return returnLocation;
    }
    
    public void deleteLocationById(Integer id) {
        locationRepository.deleteById(id);
    }
    
    public Location getLocationById(Integer id) {
        return locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location was not found for " + id));
    }
    
    public List<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        locationRepository.findAll().iterator().forEachRemaining(locations::add);
        return locations;
    }
    
    public Location updateLocationById(Integer id, UpdateLocationRequest locationRequest) {
        Location oldLocation = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location was not found for " + id));
        Location newLocation = locationMapper.update(locationRequest, oldLocation);
        return locationRepository.save(newLocation);
    }
    
    private void publish(Location location) {
        
        LocationCreatedEvent event = new LocationCreatedEvent(UUID.randomUUID().toString(), location);
        
        log.info("Publishing an location created event {}", event);
        
        publisher.publishEvent(event);
        
    }
    
    @Transactional
    public void updateLocationAsFinished(Location location) {
        
        log.info("Updating Location {} to {}", location, SagaStatus.FINISHED);
        
        Optional<Location> locationOptional = locationRepository.findById(location.getLocationId());
        
        if (locationOptional.isPresent()) {
            
            Location updateLocation = locationOptional.get();
            updateLocation.setStatus(SagaStatus.FINISHED);
            updateLocation.setOpeningHours(location.getOpeningHours());
            locationRepository.save(updateLocation);
            
            log.info("Location {} done", updateLocation.getLocationId());
            
        } else {
            
            log.error("Cannot update Location to status {}, Location {} not found", SagaStatus.FINISHED, location);
            
        }
    }
    
    @Transactional
    public void rejectLocation(Location location) {
        
        log.info("Canceling Location {}", location);
        
        Optional<Location> optionalLocation = locationRepository.findById(location.getLocationId());
        
        if (optionalLocation.isPresent()) {
            
            Location updateLocation = optionalLocation.get();
            updateLocation.setStatus(SagaStatus.REJECTED);
            locationRepository.save(updateLocation);
            
            log.info("Location {} was canceled", updateLocation.getLocationId());
            
        } else {
            
            log.info("Cannot find an Location {}", location.getLocationId());
            
        }
    }
}
