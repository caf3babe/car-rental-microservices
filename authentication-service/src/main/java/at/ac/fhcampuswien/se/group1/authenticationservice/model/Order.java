package at.ac.fhcampuswien.se.group1.authenticationservice.model;

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
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Data
@Document
public class Order {
    
    @Id
    @Schema(name = "order_id", required = true)
    @JsonProperty("orderId")
    @JsonSerialize(using = ToStringSerializer.class)
    private BigInteger orderId;
    
    @Valid
    @JsonProperty("car")
    @Schema(name = "car", required = true)
    private Car car;
    
    @Valid
    @JsonProperty("location_of_rental")
    @Schema(name = "location_of_rental", required = true)
    private Location locationOfRental;
    
    @Valid
    @Schema(name = "location_of_return", required = true)
    @JsonProperty("location_of_return")
    private Location locationOfReturn;
    
    @Valid
    @Schema(name = "date_of_booking", required = true)
    @JsonProperty("date_of_booking")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBooking;
    
    @Valid
    @Schema(name = "date_of_rental", required = true)
    @JsonProperty("date_of_rental")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfRental;
    
    @Valid
    @Schema(name = "date_of_return", required = true)
    @JsonProperty("date_of_return")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfReturn;
    
    @Valid
    @Schema(name = "method_of_payment", required = true)
    @JsonProperty("method_of_payment")
    private Payment payment;
    
    @Valid
    @Schema(name = "card_number", required = true)
    @JsonProperty("card_number")
    private String cardNumber;
    
    @Valid
    @Schema(name = "card_security_code", required = true)
    @JsonProperty("card_security_code")
    private String cardSecurityCode;
    
    @Valid
    @Schema(name = "valid_until", required = true)
    @JsonProperty("valid_until")
    private Date validUntil;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 64)
    @Schema(name = "first_name", required = true)
    @JsonProperty("first_name")
    private String firstName;
    
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 64)
    @Schema(name = "last_name", required = true)
    @JsonProperty("last_name")
    private String lastName;
    
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    @Size(max = 64)
    @Email
    @Schema(name = "email", required = true)
    @JsonProperty("email")
    private String email;
    
    @NotNull
    @Schema(name = "order_status", required = true)
    @JsonProperty("order_status")
    private OrderStatus orderStatus;

    @Schema(name = "saga_status")
    @JsonProperty("saga_status")
    private SagaStatus status;
    
}

