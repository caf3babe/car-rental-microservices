package at.ac.fhcampuswien.se.group1.locationservice.controller;

import at.ac.fhcampuswien.se.group1.locationservice.domain.dto.CreateLocationRequest;
import at.ac.fhcampuswien.se.group1.locationservice.domain.dto.UpdateLocationRequest;
import at.ac.fhcampuswien.se.group1.locationservice.model.ApiError;
import at.ac.fhcampuswien.se.group1.locationservice.model.Location;
import at.ac.fhcampuswien.se.group1.locationservice.model.Response;
import at.ac.fhcampuswien.se.group1.locationservice.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigInteger;
import java.util.List;

@Validated
@Tag(name = "location", description = "the location API")
@RestController
@RequestMapping("/api/v1")
public class LocationController {
    
    private final LocationService locationService;
    
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }
    
    /**
     * POST /location : Create a new location
     *
     * @param locationRequest New location data (required)
     * @return Successfully created (status code 201)
     * or Invalid request message (status code 400)
     */
    @Operation(operationId = "createLocation", summary = "Create a new location", tags = {"location"}, responses = {
            @ApiResponse(responseCode = "201", description = "Successfully created", content = {
                    @Content(mediaType = "application/json", examples = {@ExampleObject(value = """
                                                                  {
                                        "location_id": 1,
                                        "opening_hours_id": {
                                          "opening_hours_id": 1,
                                          "monday": "07.00 Uhr - 23.30 Uhr",
                                          "tuesday": "07.00 Uhr - 23.30 Uhr",
                                          "wednesday": "07.00 Uhr - 23.30 Uhr",
                                          "thursday": "07.00 Uhr - 23.30 Uhr",
                                          "friday": "07.00 Uhr - 23.30 Uhr",
                                          "saturday": "08.00 Uhr - 20.00 Uhr",
                                          "sunday": "08.00 Uhr - 23.00 Uhr"
                                        },
                                        "location_name": "Airport Vienna",
                                        "street_name": "Parkstrasse",
                                        "street_number": "16",
                                        "city_name": "Austria",
                                        "postal_code": 1300,
                                        "email": "airport-vienna@carrentalvienna.com",
                                        "phone": "06602526284",
                                        "latitude": "48.12037524536211",
                                        "longitude": "16.563466629953894"
                                      }
                            """)}, schema = @Schema(implementation = Location.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid request message", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @PostMapping(value = "/location", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<Location> createLocation(
            @Parameter(name = "Location", description = "New location data", required = true) @Valid @RequestBody
                    CreateLocationRequest locationRequest) {
        return new ResponseEntity<>(locationService.createLocation(locationRequest), HttpStatus.CREATED);
    }
    
    /**
     * DELETE /location/{id} : Delete a location by id
     *
     * @param id The id of the location to update (required)
     * @return Successfully deleted (status code 200)
     * or Invalid Location Id (status code 400)
     * or Location not found (status code 404)
     */
    @Operation(operationId = "deleteLocationById", summary = "Delete a location by id", tags = {"location"},
            responses = {@ApiResponse(responseCode = "200", description = "Successfully deleted", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = Response.class))}),
                    @ApiResponse(responseCode = "400", description = "Invalid Location Id", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))}),
                    @ApiResponse(responseCode = "404", description = "Location not found", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ApiError.class))})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @DeleteMapping(value = "/location/{id}", produces = {"application/json"})
    public ResponseEntity<Response> deleteLocationById(
            @Parameter(name = "id", description = "The id of the location to update", required = true)
            @PathVariable("id") BigInteger id) {
        locationService.deleteLocationById(id);
        Response response = new Response();
        response.setMessage(String.format("Successfully deleted location with id %s", id));
        return ResponseEntity.ok(response);
    }
    
    /**
     * GET /location/{id} : Get a location by id
     *
     * @param id The id of the location to retrieve (required)
     * @return Successful Operation (status code 200)
     * or Invalid ID supplied (status code 400)
     * or Location not found (status code 404)
     */
    @Operation(operationId = "getLocationById", summary = "Get a location by id", tags = {"location"}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = {
                    @Content(mediaType = "application/json", examples = {@ExampleObject(value = """
                                                                  {
                                        "location_id": 1,
                                        "opening_hours_id": {
                                          "opening_hours_id": 1,
                                          "monday": "07.00 Uhr - 23.30 Uhr",
                                          "tuesday": "07.00 Uhr - 23.30 Uhr",
                                          "wednesday": "07.00 Uhr - 23.30 Uhr",
                                          "thursday": "07.00 Uhr - 23.30 Uhr",
                                          "friday": "07.00 Uhr - 23.30 Uhr",
                                          "saturday": "08.00 Uhr - 20.00 Uhr",
                                          "sunday": "08.00 Uhr - 23.00 Uhr"
                                        },
                                        "location_name": "Airport Vienna",
                                        "street_name": "Parkstrasse",
                                        "street_number": "16",
                                        "city_name": "Austria",
                                        "postal_code": 1300,
                                        "email": "airport-vienna@carrentalvienna.com",
                                        "phone": "06602526284",
                                        "latitude": "48.12037524536211",
                                        "longitude": "16.563466629953894"
                                      }
                            """)}, schema = @Schema(implementation = Location.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Location Id", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404", description = "Location not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})
    @GetMapping(value = "/location/{id}", produces = {"application/json"})
    public ResponseEntity<Location> getLocationById(
            @Parameter(name = "id", description = "The id of the location to retrieve", required = true)
            @PathVariable("id") BigInteger id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }
    
    /**
     * GET /location : Get a list of locations
     *
     * @return Successful Operation (status code 200)
     * or empty list when no locations found (status code 204)
     */
    @Operation(operationId = "getLocations", summary = "Get a list of locations", tags = {"location"}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful Operation", content = {
                    @Content(mediaType = "application/json",
                            examples = {@ExampleObject(value = """
                                    [
                                      {
                                        "location_id": 1,
                                        "opening_hours_id": {
                                          "opening_hours_id": 1,
                                          "monday": "07.00 Uhr - 23.30 Uhr",
                                          "tuesday": "07.00 Uhr - 23.30 Uhr",
                                          "wednesday": "07.00 Uhr - 23.30 Uhr",
                                          "thursday": "07.00 Uhr - 23.30 Uhr",
                                          "friday": "07.00 Uhr - 23.30 Uhr",
                                          "saturday": "08.00 Uhr - 20.00 Uhr",
                                          "sunday": "08.00 Uhr - 23.00 Uhr"
                                        },
                                        "location_name": "Airport Vienna",
                                        "street_name": "Parkstrasse",
                                        "street_number": "16",
                                        "city_name": "Austria",
                                        "postal_code": 1300,
                                        "email": "airport-vienna@carrentalvienna.com",
                                        "phone": "06602526284",
                                        "latitude": "48.12037524536211",
                                        "longitude": "16.563466629953894"
                                      },
                                      {
                                        "location_id": 2,
                                        "opening_hours_id": {
                                          "opening_hours_id": 2,
                                          "monday": "09.00 Uhr - 23.30 Uhr",
                                          "tuesday": "07.00 Uhr - 20.30 Uhr",
                                          "wednesday": "07.00 Uhr - 23.30 Uhr",
                                          "thursday": "07.00 Uhr - 23.30 Uhr",
                                          "friday": "07.00 Uhr - 23.30 Uhr",
                                          "saturday": "08.00 Uhr - 20.00 Uhr",
                                          "sunday": "10.00 Uhr - 20.00 Uhr"
                                        },
                                        "location_name": "City Vienna",
                                        "street_name": "Stephansplatz",
                                        "street_number": "6",
                                        "city_name": "Austria",
                                        "postal_code": 1300,
                                        "email": "city-vienna@carrentalvienna.com",
                                        "phone": "06602526285",
                                        "latitude": "48.20852573292344",
                                        "longitude": "16.374050059536025"
                                      }
                                    ]
                                    """)},
                            array = @ArraySchema(schema = @Schema(implementation = Location.class)))}),
            @ApiResponse(responseCode = "204", description = "Successful Operation but no content found")})
    @GetMapping(value = "/location", produces = {"application/json"})
    public ResponseEntity<List<Location>> getLocations() {
        List<Location> locations = locationService.getLocations();
        return locations.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(locations);
    }
    
    /**
     * PUT /location/{id} : Update location by id
     *
     * @param id              The id of the location to update (required)
     * @param locationRequest Updated location object (required)
     * @return Successful operation (status code 200)
     * or Invalid ID supplied (status code 400)
     * or Location not found (status code 404)
     * or Validation exception (status code 405)
     */
    @Operation(operationId = "updateLocationById", summary = "Update location by id", tags = {"location"}, responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = {
                    @Content(mediaType = "application/json", examples = {@ExampleObject(value = """
                                                                  {
                                        "location_id": 2,
                                        "opening_hours_id": {
                                          "opening_hours_id": 2,
                                          "monday": "09.00 Uhr - 23.30 Uhr",
                                          "tuesday": "07.00 Uhr - 20.30 Uhr",
                                          "wednesday": "07.00 Uhr - 23.30 Uhr",
                                          "thursday": "07.00 Uhr - 23.30 Uhr",
                                          "friday": "07.00 Uhr - 23.30 Uhr",
                                          "saturday": "08.00 Uhr - 20.00 Uhr",
                                          "sunday": "10.00 Uhr - 20.00 Uhr"
                                        },
                                        "location_name": "City Vienna",
                                        "street_name": "Stephansplatz",
                                        "street_number": "6",
                                        "city_name": "Austria",
                                        "postal_code": 1300,
                                        "email": "city-vienna@carrentalvienna.com",
                                        "phone": "06602526285",
                                        "latitude": "48.20852573292344",
                                        "longitude": "16.374050059536025"
                                      }
                            """)}, schema = @Schema(implementation = UpdateLocationRequest.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid Location Id", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "404", description = "Location not found", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))}),
            @ApiResponse(responseCode = "405", description = "Validation exception", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})},
            security = {@SecurityRequirement(name = "bearerAuth")})
    @PutMapping(value = "/location/{id}", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<Location> updateLocationById(
            @Parameter(name = "id", description = "The id of the location to update", required = true)
            @PathVariable("id") BigInteger id,
            @Parameter(name = "body", description = "Updated location object", required = true) @Valid @RequestBody
                    UpdateLocationRequest locationRequest) {
        return ResponseEntity.ok(locationService.updateLocationById(id, locationRequest));
    }
    
}
