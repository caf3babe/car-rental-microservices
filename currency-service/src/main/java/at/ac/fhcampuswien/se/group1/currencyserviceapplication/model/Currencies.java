package at.ac.fhcampuswien.se.group1.currencyserviceapplication.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Document
public class Currencies {
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date date;
    
    private List<Currency> currencyList;
    
}
