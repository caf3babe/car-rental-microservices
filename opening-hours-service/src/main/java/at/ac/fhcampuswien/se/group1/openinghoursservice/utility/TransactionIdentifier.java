package at.ac.fhcampuswien.se.group1.openinghoursservice.utility;

import org.springframework.stereotype.Component;

@Component
public class TransactionIdentifier {
    
    private final ThreadLocal<String> id = new ThreadLocal<>();
    
    public String getId() {
        return id.get();
    }
    
    public void setId(String value) {
        id.set(value);
    }

    public void unload(){
        id.remove();
    }
}
