package at.ac.fhcampuswien.se.group1.domain.exception;

public class CarNotFoundException extends RuntimeException {
    public CarNotFoundException(String message){
        super(message);
    }
}
