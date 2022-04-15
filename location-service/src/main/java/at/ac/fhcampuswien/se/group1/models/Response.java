package at.ac.fhcampuswien.se.group1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Response {
    @NotNull
    @Pattern(regexp = "^([\\w. ]+)$")
    @Size(max = 250)
    @Schema(name = "message", description = "The description message for this success code", required = true, example = "Successfully deleted object with id 3")
    @JsonProperty("message")
    private String message;
}

