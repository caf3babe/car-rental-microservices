package at.ac.fhcampuswien.se.group1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

@Data
@Document
@AllArgsConstructor
public class Location {

    @MongoId
    @Field(name = "location_id")
    @JsonProperty("location_id")
    private Integer locationId;

    @JsonProperty("opening_hours_id")
    private Integer openingHoursId;

    @Size(max = 64)
    @Field(name = "location_name")
    @JsonProperty("location_name")
    private String locationName;

    @Size(max = 32)
    @Field(name = "street_name")
    @JsonProperty("street_name")
    private String streetName;

    @Size(max = 32)
    @Field(name = "street_number")
    @JsonProperty("street_number")
    private String streetNumber;

    @Size(max = 32)
    @Field(name = "city_name")
    @JsonProperty("city_name")
    private String cityName;

    @Min(1000)
    @Max(999999)
    @Field(name = "postal_code")
    @JsonProperty("postal_code")
    private Integer postalCode;

    @Size(max = 64)
    @Field(name = "email")
    @JsonProperty("email")
    private String email;

    @Size(max = 32)
    @Field(name = "phone")
    @JsonProperty("phone")
    private String phone;

    @Size(max = 64)
    @Field(name = "latitude")
    @JsonProperty("latitude")
    private String latitude;

    @Size(max = 64)
    @Field(name = "longitude")
    @JsonProperty("longitude")
    private String longitude;

}

