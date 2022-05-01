package at.ac.fhcampuswien.se.group1.currencyserviceapplication.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Document
public class Currency {
    
    @JsonProperty("symbol")
    @Field("symbol")
    private String currencySymbol;
    @JsonProperty("rate")
    private double rate;
    
}
