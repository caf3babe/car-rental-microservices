package at.ac.fhcampuswien.se.group1.carservice.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

@ToString
public enum OrderStatus {
    CREATED, CANCELED, ACTIVE, RETURNED;
    
    @JsonValue
    public String getValue() {
        return this.name();
    }
    
}
