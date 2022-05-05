package at.ac.fhcampuswien.se.group1.authenticationservice.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Email;

@Data
public class LoginAdminRequest {
    @JsonProperty("email")
    @Email
    @Schema(name = "email", type = "email", example = "admin@admin.io", required = true)
    private final String email;
    @JsonProperty("password")
    @Schema(name = "password", example = "admin123", required = true)
    private final String password;
}
