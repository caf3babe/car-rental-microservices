package at.ac.fhcampuswien.se.group1.orderservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Location {
    
    @Valid
    @Schema(name = "location_id")
    @JsonProperty("location_id")
    private Integer locationId;
    
    @JsonProperty("opening_hours")
    private OpeningHours openingHours;
    
    @Size(max = 64)
    @Schema(name = "location_name")
    @JsonProperty("location_name")
    private String locationName;
    
    @Size(max = 32)
    @Schema(name = "street_name")
    @JsonProperty("street_name")
    private String streetName;
    
    @Size(max = 32)
    @Schema(name = "street_number")
    @JsonProperty("street_number")
    private String streetNumber;
    
    @Size(max = 32)
    @Schema(name = "city_name")
    @JsonProperty("city_name")
    private String cityName;
    
    @Min(1000)
    @Max(999999)
    @Schema(name = "postal_code")
    @JsonProperty("postal_code")
    private Integer postalCode;
    
    @Size(max = 64)
    @Schema(name = "email")
    @JsonProperty("email")
    private String email;
    
    @Size(max = 32)
    @Schema(name = "phone")
    @JsonProperty("phone")
    private String phone;
    
    @Size(max = 64)
    @Schema(name = "latitude")
    @JsonProperty("latitude")
    private String latitude;
    
    @Size(max = 64)
    @Schema(name = "longitude")
    @JsonProperty("longitude")
    private String longitude;

    @Schema(name = "saga_status")
    @JsonProperty("saga_status")
    private SagaStatus status;
}

