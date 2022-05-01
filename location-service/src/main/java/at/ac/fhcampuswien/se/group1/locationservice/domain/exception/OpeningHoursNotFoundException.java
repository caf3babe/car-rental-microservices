package at.ac.fhcampuswien.se.group1.locationservice.domain.exception;

public class OpeningHoursNotFoundException extends RuntimeException {
    
    public OpeningHoursNotFoundException(String message) {
        super(message);
    }
}
