package at.ac.fhcampuswien.se.group1.locationservice.utility;

import at.ac.fhcampuswien.se.group1.locationservice.model.Location;
import at.ac.fhcampuswien.se.group1.locationservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.locationservice.model.SagaStatus;
import at.ac.fhcampuswien.se.group1.locationservice.repository.LocationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
public class SeedDatabase implements CommandLineRunner {
    
    private LocationRepository locationRepository;
    
    public SeedDatabase(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }
    
    @Override
    public void run(String... args) {
        
        OpeningHours openingHoursDatasetOne = new OpeningHours(1, "06.00 Uhr - 23.30 Uhr",
                "08.00 Uhr - 23.30 Uhr",
                "10.00 Uhr - 23.30 Uhr",
                "11.00 Uhr - 23.30 Uhr",
                "07.30 Uhr - 23.30 Uhr",
                "06.00 Uhr - 20.00 Uhr",
                "05.00 Uhr - 23.00 Uhr");
        
        OpeningHours openingHoursDatasetTwo = new OpeningHours(2, "08.00 Uhr - 23.30 Uhr",
                "06.15 Uhr - 23.30 Uhr",
                "07.10 Uhr - 23.30 Uhr",
                "07.00 Uhr - 23.30 Uhr",
                "08.00 Uhr - 20.30 Uhr",
                "10.10 Uhr - 19.00 Uhr",
                "08.20 Uhr - 23.00 Uhr");
        
        
        Location locationDatasetOne =
                new Location(BigInteger.ONE, openingHoursDatasetOne, "Airport Vienna", "Parkstrasse", "16", "Schwechat", 1300,
                        "airport-vienna@carrentalvienna.com", "06602526284", "48.12037524536211", "16.563466629953894",
                        SagaStatus.FINISHED);
        
        Location locationDatasetTwo =
                new Location(BigInteger.TWO, openingHoursDatasetTwo, "Vienna Centre", "Stephansplatz", "6", "Vienna", 1010,
                        "city-vienna@carrentalvienna.com", "06602526284", "48.20852573292344", "16.374050059536025",
                        SagaStatus.FINISHED);
        
        this.locationRepository.deleteAll();
        
        this.locationRepository.save(locationDatasetOne);
        this.locationRepository.save(locationDatasetTwo);
        
    }
}
