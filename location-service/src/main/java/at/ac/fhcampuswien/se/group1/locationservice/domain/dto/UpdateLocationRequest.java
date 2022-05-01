package at.ac.fhcampuswien.se.group1.locationservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UpdateLocationRequest {
    
    @Schema(name = "location_name", required = true, example = "Linz Hauptbahnhof")
    @JsonProperty("location_name")
    private String locationName;
    
    @Schema(name = "street_name", required = true, example = "Bahnhofplatz")
    @JsonProperty("street_name")
    private String streetName;
    
    @Schema(name = "street_number", required = true, example = "3-6")
    @JsonProperty("street_number")
    private String streetNumber;
    
    @Schema(name = "city_name", required = true, example = "Linz")
    @JsonProperty("city_name")
    private String cityName;
    
    @Schema(name = "postal_code", required = true, example = "4020")
    @JsonProperty("postal_code")
    private Integer postalCode;
    
    @Schema(name = "email", required = true, example = "linz@oebb.at")
    @JsonProperty("email")
    private String email;
    
    @Schema(name = "phone", required = true, example = "06606333209")
    @JsonProperty("phone")
    private String phone;
    
    @Schema(name = "latitude", required = true, example = "20.21238798787")
    @JsonProperty("latitude")
    private String latitude;
    
    @Schema(name = "longitude", required = true, example = "43.09835465467")
    @JsonProperty("longitude")
    private String longitude;
}
