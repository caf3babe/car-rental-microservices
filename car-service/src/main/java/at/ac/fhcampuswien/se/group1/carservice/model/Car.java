package at.ac.fhcampuswien.se.group1.carservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;

@AllArgsConstructor
@Data
@Document
public class Car {
    
    @Id
    @Valid
    @Schema(name = "car_id", required = true, example = "1")
    @JsonProperty("car_id")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger carId;
    
    @Schema(name = "car_status", required = true, example = "AVAILABLE")
    @JsonProperty("car_status")
    private CarStatus carStatus;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 64)
    @Schema(name = "chassis_number", required = true, example = "W0L000051T2123456")
    @JsonProperty("chassis_number")
    private String chassisNumber;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 32)
    @Schema(name = "manufacturer", required = true, example = "Audi")
    @JsonProperty("manufacturer")
    private String manufacturer;
    
    @Valid
    @Schema(name = "construction_year", required = true, example = "2010")
    @JsonProperty("construction_year")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate constructionYear;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 32)
    @Schema(name = "color", required = true, example = "Black")
    @JsonProperty("color")
    private String color;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 32)
    @Schema(name = "model", required = true, example = "TT")
    @JsonProperty("model")
    private String model;
    
    @Schema(name = "model_series", required = true, example = "Coupé")
    @JsonProperty("model_series")
    private String modelSeries;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 32)
    @Schema(name = "engine_fuel", required = true, example = "Diesel")
    @JsonProperty("engine_fuel")
    private String engineFuel;
    
    @Valid
    @DecimalMin("0")
    @DecimalMax("100")
    @Schema(name = "engine_fuel_consumption", required = true, example = "3.5")
    @JsonProperty("engine_fuel_consumption")
    private BigDecimal engineFuelConsumption;
    
    @Min(0)
    @Max(1500)
    @Schema(name = "engine_performance", required = true, example = "300")
    @JsonProperty("engine_performance")
    private Integer enginePerformance;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 32)
    @Schema(name = "engine_type", required = true, example = "Hybrid")
    @JsonProperty("engine_type")
    private String engineType;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 32)
    @Schema(name = "gear_type", required = true, example = "automatic")
    @JsonProperty("gear_type")
    private String gearType;
    
    @Schema(name = "adblue", required = true, example = "false")
    @JsonProperty("adblue")
    private Boolean adblue;
    
    @Min(2)
    @Max(8)
    @Schema(name = "seats", required = true, example = "2")
    @JsonProperty("seats")
    private Integer seats;
    
    @Valid
    @DecimalMin("0")
    @DecimalMax("999999")
    @Schema(name = "price", required = true, example = "65000.00")
    @JsonProperty("price")
    private Double price;
    
    @Valid
    @Schema(name = "currency_symbol", required = true, example = "USD")
    @JsonProperty("currency_symbol")
    private CurrencySymbol currencySymbol;
    
    @Valid
    @Schema(name = "picture_link", required = true,
            example = "https://www.auto-data.net/en/audi-tt-rs-roadster-8s-facelift-2019-generation-7105#image8")
    @JsonProperty("picture_link")
    private String pictureLink;
}
