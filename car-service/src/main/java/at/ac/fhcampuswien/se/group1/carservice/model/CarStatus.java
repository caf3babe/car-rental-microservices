package at.ac.fhcampuswien.se.group1.carservice.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

@ToString
public enum CarStatus {
    REPAIR, AVAILABLE, RENTED;
    
    @JsonValue
    public String getValue() {
        return this.name();
    }
}
