package at.ac.fhcampuswien.se.group1.orderservice.domain.dto;

import at.ac.fhcampuswien.se.group1.orderservice.model.OrderStatus;
import at.ac.fhcampuswien.se.group1.orderservice.model.Payment;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Data
public class OrderRequest {
    
    @Valid
    @JsonProperty("car_id")
    @Schema(name = "car_id", required = true, example = "1")
    private Integer carId;
    
    @Valid
    @JsonProperty("location_of_rental_id")
    @Schema(name = "location_of_rental_id", required = true, example = "2")
    private Integer locationOfRentalId;
    
    @Valid
    @Schema(name = "location_of_return_id", required = true, example = "1")
    @JsonProperty("location_of_return_id")
    private Integer locationOfReturnId;
    
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
    @Schema(name = "card_number", required = true, example = "4274323216727548")
    @JsonProperty("card_number")
    private String cardNumber;
    
    @Valid
    @Schema(name = "card_security_code", required = true, example = "976")
    @JsonProperty("card_security_code")
    private String cardSecurityCode;
    
    @Valid
    @Schema(name = "valid_until", required = true)
    @JsonProperty("valid_until")
    private Date validUntil;
    
    @Size(max = 64)
    @Schema(name = "first_name", required = true, example = "Max")
    @JsonProperty("first_name")
    private String firstName;
    
    @Size(max = 64)
    @Schema(name = "last_name", required = true, example = "Schneider")
    @JsonProperty("last_name")
    private String lastName;
    
    @Size(max = 64)
    @Email
    @Schema(name = "email", required = true, example = "max.schneider@outlook.com")
    @JsonProperty("email")
    private String email;
    
    @NotNull
    @Schema(name = "order_status", required = true)
    @JsonProperty("order_status")
    private OrderStatus orderStatus;
}
