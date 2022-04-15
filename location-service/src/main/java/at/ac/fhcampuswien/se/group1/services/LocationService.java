package at.ac.fhcampuswien.se.group1.services;

import at.ac.fhcampuswien.se.group1.domain.dto.CreateLocationRequest;
import at.ac.fhcampuswien.se.group1.domain.dto.UpdateLocationRequest;
import at.ac.fhcampuswien.se.group1.domain.exception.LocationNotFoundException;
import at.ac.fhcampuswien.se.group1.domain.mapper.LocationMapper;
import at.ac.fhcampuswien.se.group1.models.Location;
import at.ac.fhcampuswien.se.group1.repositories.LocationRepository;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class LocationService {

    private final LocationRepository locationRepository;

    private final LocationMapper locationMapper;

    public Location createLocation(CreateLocationRequest locationRequest) {
        Location location = locationMapper.create(locationRequest);
//        location.setOpeningHours(openingHoursRepository.findById(locationRequest.getOpeningHoursId())
//                .orElseThrow(()-> new OpeningHoursNotFoundException("Opening Hours was not found for "+ locationRequest.getOpeningHoursId())));
          return locationRepository.save(location);
    }

    public void deleteLocationById(Integer id) {
        locationRepository.deleteById(id);
    }

    public Location getLocationById(Integer id) {
        return locationRepository.findById(id).orElseThrow(()->new LocationNotFoundException("Location was not found for "+ id));
    }

    public List<Location> getLocations() {
        ArrayList<Location> locations = new ArrayList<>();
        locationRepository.findAll().iterator().forEachRemaining(locations::add);
        return locations;
    }

    public Location updateLocationById(Integer id, UpdateLocationRequest locationRequest) {
        Location oldLocation = locationRepository.findById(id).orElseThrow(()-> new LocationNotFoundException("Location was not found for "+ id));
        Location newLocation = locationMapper.update(locationRequest, oldLocation);
        return locationRepository.save(newLocation);
    }
}
