package at.ac.fhcampuswien.se.group1.openinghoursservice.controller;

import at.ac.fhcampuswien.se.group1.openinghoursservice.model.ApiError;
import at.ac.fhcampuswien.se.group1.openinghoursservice.model.OpeningHours;
import at.ac.fhcampuswien.se.group1.openinghoursservice.service.OpeningHoursService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@Tag(name = "opening hours", description = "the opening hours API")
@RestController
@RequestMapping("/api/v1")
public class OpeningHoursController {
    
    private final OpeningHoursService openingHoursService;
    
    public OpeningHoursController(OpeningHoursService openingHoursService) {
        this.openingHoursService = openingHoursService;
    }
    
    /**
     * GET /location/{id} : Get a opening hour by id
     *
     * @param id The id of the opening hours to retrieve (required)
     * @return Successful Operation (status code 200)
     * or Invalid ID supplied (status code 400)
     * or Opening Hours not found (status code 404)
     */
    @Operation(operationId = "getOpeningHoursById", summary = "Get a opening hours by id", tags = {"opening hours"},
            responses = {@ApiResponse(responseCode = "200", description = "Successful Operation", content = {
                    @Content(mediaType = "application/json", examples = {@ExampleObject(value = """
                                                                  {
                                "opening_hours_id": 1,
                                "monday": "07.00 Uhr - 23.30 Uhr",
                                "tuesday": "07.00 Uhr - 23.30 Uhr",
                                "wednesday": "07.00 Uhr - 23.30 Uhr",
                                "thursday": "07.00 Uhr - 23.30 Uhr",
                                "friday": "07.00 Uhr - 23.30 Uhr",
                                "saturday": "08.00 Uhr - 20.00 Uhr",
                                "sunday": "08.00 Uhr - 23.00 Uhr"
                              }
                            """)}, schema = @Schema(implementation = OpeningHours.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid opening hours Id", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))}),
                    @ApiResponse(responseCode = "404", description = "Opening hours not found", content = {
                            @Content(mediaType = "application/json", examples = {@ExampleObject(value = """
                                    {
                                      "timestamp": "06-04-2022 06:40:40",
                                      "status": "NOT_FOUND",
                                      "message": "Opening Hours was not found for 9",
                                      "errors": []
                                    }
                                    """)},
                                    schema = @Schema(implementation = ApiError.class))})})
    @GetMapping(value = "/opening-hours/{id}", produces = {"application/json"})
    public ResponseEntity<OpeningHours> getOpeningHoursById(
            @Parameter(name = "id", description = "The id of the opening hours to retrieve", required = true)
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(openingHoursService.getOpeningHoursById(id));
    }

    /**
     * GET /location : Get a list of opening hours
     *
     * @return Successful Operation (status code 200)
     * or empty list if not Locations found ( status code 204 )
     */
    @Operation(operationId = "getAllOpeningHours", summary = "Get a list of opening hours", tags = {"opening hours"},
            responses = {@ApiResponse(responseCode = "200", description = "Successful Operation", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = """
                                    [
                                      {
                                        "opening_hours_id": 1,
                                        "monday": "07.00 Uhr - 23.30 Uhr",
                                        "tuesday": "07.00 Uhr - 23.30 Uhr",
                                        "wednesday": "07.00 Uhr - 23.30 Uhr",
                                        "thursday": "07.00 Uhr - 23.30 Uhr",
                                        "friday": "07.00 Uhr - 23.30 Uhr",
                                        "saturday": "08.00 Uhr - 20.00 Uhr",
                                        "sunday": "08.00 Uhr - 23.00 Uhr"
                                      },
                                      {
                                        "opening_hours_id": 2,
                                        "monday": "09.00 Uhr - 23.30 Uhr",
                                        "tuesday": "07.00 Uhr - 20.30 Uhr",
                                        "wednesday": "07.00 Uhr - 23.30 Uhr",
                                        "thursday": "07.00 Uhr - 23.30 Uhr",
                                        "friday": "07.00 Uhr - 23.30 Uhr",
                                        "saturday": "08.00 Uhr - 20.00 Uhr",
                                        "sunday": "10.00 Uhr - 20.00 Uhr"
                                      },
                                                                          {
                                        "opening_hours_id": 3,
                                        "monday": "09.00 Uhr - 23.30 Uhr",
                                        "tuesday": "10.00 Uhr - 20.30 Uhr",
                                        "wednesday": "07.00 Uhr - 23.30 Uhr",
                                        "thursday": "08.00 Uhr - 23.30 Uhr",
                                        "friday": "15.00 Uhr - 23.30 Uhr",
                                        "saturday": "08.00 Uhr - 20.00 Uhr",
                                        "sunday": "10.00 Uhr - 20.00 Uhr"
                                      }
                                    ]
                                    """)
                    },
                            array = @ArraySchema(schema = @Schema(implementation = OpeningHours.class)))}),
                    @ApiResponse(responseCode = "204", description = "Successful Operation but no content found")})
    @GetMapping(value = "/opening-hours", produces = {"application/json"})
    public ResponseEntity<List<OpeningHours>> getAllOpeningHours() {
        List<OpeningHours> openingHours = openingHoursService.getAllOpeningHours();
        return openingHours.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(openingHours);
    }
}