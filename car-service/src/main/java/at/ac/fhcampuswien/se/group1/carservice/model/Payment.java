package at.ac.fhcampuswien.se.group1.carservice.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.ToString;

@ToString
public enum Payment {
    DINERS_CLUB, VISA, MASTERCARD, AMERICAN_EXPRESS;
    
    @JsonValue
    public String getValue() {
        return this.name();
    }
}
