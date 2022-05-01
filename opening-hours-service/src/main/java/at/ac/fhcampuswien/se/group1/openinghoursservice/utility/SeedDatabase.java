package at.ac.fhcampuswien.se.group1.openinghoursservice.utility;

import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.openinghoursservice.repository.OpeningHoursRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//TODO Replace with insert in Docker Enviornment later
@Component
public class SeedDatabase implements CommandLineRunner {
    
    private OpeningHoursRepository openingHoursRepository;
    
    public SeedDatabase(OpeningHoursRepository openingHoursRepository) {
        this.openingHoursRepository = openingHoursRepository;
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
        
        this.openingHoursRepository.deleteAll();
        
        this.openingHoursRepository.save(openingHoursDatasetOne);
        this.openingHoursRepository.save(openingHoursDatasetTwo);
        
    }
}
