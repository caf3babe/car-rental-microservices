package at.ac.fhcampuswien.se.group1.openinghoursservice.model;

public class OpeningHoursNotFoundException extends RuntimeException {
    
    public OpeningHoursNotFoundException(String message) {
        super(message);
    }
}
