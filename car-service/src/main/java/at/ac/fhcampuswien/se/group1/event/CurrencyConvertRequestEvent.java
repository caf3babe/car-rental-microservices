package at.ac.fhcampuswien.se.group1.event;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CurrencyConvertRequestEvent {
    private String destinationSymbol;
    private String sourceSymbol;
    private Double sourceValue;
}
