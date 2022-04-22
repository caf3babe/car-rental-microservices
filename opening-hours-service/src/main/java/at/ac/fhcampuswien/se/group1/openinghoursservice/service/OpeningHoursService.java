package at.ac.fhcampuswien.se.group1.openinghoursservice.service;

import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHoursNotFoundException;
import at.ac.fhcampuswien.se.group1.openinghoursservice.repository.OpeningHoursRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OpeningHoursService {

    private final OpeningHoursRepository openingHoursRepository;

    public OpeningHoursService(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
    }

    public List<OpeningHours> getAllOpeningHours() {
        List<OpeningHours> openingHours = new ArrayList<>();
        openingHoursRepository.findAll().iterator().forEachRemaining(openingHours::add);
        return openingHours;
    }

    public OpeningHours getOpeningHoursById(Integer id) {
        return openingHoursRepository.findById(id).orElseThrow(()-> new OpeningHoursNotFoundException("Opening Hours was not found for "+ id));
    }
}
