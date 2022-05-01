package at.ac.fhcampuswien.se.group1.locationservice.utility;

import org.springframework.stereotype.Component;

@Component
public class TransactionId {
    
    private final ThreadLocal<String> transactionId = new ThreadLocal<>();
    
    public String getTransactionId() {
        
        return transactionId.get();
        
    }
    
    public void setTransactionId(String value) {
        
        transactionId.set(value);
        
    }
}
