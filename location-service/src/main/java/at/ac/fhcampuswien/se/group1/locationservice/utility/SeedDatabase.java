package at.ac.fhcampuswien.se.group1.locationservice.utility;

import at.ac.fhcampuswien.se.group1.locationservice.model.Location;
import at.ac.fhcampuswien.se.group1.locationservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.locationservice.model.SagaStatus;
import at.ac.fhcampuswien.se.group1.locationservice.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//TODO Replace with insert in Docker Enviornment later
@Component
public class SeedDatabase implements CommandLineRunner {
    
    private LocationRepository locationRepository;
    
    public SeedDatabase(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    
    @Override
    public void run(String... args) {
        
        OpeningHours openingHoursDatasetOne = new OpeningHours(1, "07.00 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "08.00 Uhr - 20.00 Uhr",
                "08.00 Uhr - 23.00 Uhr");
        
        OpeningHours openingHoursDatasetTwo = new OpeningHours(2, "08.00 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "09.00 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "08.00 Uhr - 19.00 Uhr",
                "08.00 Uhr - 23.00 Uhr");
        
        
        Location locationDatasetOne =
                new Location(1, openingHoursDatasetOne, "Airport Vienna", "Parkstrasse", "16", "Schwechat", 1300,
                        "airport-vienna@carrentalvienna.com", "06602526284", "48.12037524536211", "16.563466629953894",
                        SagaStatus.FINISHED);
        
        Location locationDatasetTwo =
                new Location(2, openingHoursDatasetTwo, "Vienna Centre", "Stephansplatz", "1", "Vienna", 1010,
                        "stephansplatz@carrentalvienna.com", "06602526284", "44.12037524536211", "18.563466629953894",
                        SagaStatus.FINISHED);
        
        this.locationRepository.deleteAll();
        
        this.locationRepository.save(locationDatasetOne);
        this.locationRepository.save(locationDatasetTwo);
        
    }
}
