package at.ac.fhcampuswien.se.group1.authenticationservice.controller;

import at.ac.fhcampuswien.se.group1.authenticationservice.domain.dto.AuthenticationResponse;
import at.ac.fhcampuswien.se.group1.authenticationservice.domain.dto.LoginAdminRequest;
import at.ac.fhcampuswien.se.group1.authenticationservice.domain.dto.LoginOrderRequest;
import at.ac.fhcampuswien.se.group1.authenticationservice.model.ApiError;
import at.ac.fhcampuswien.se.group1.authenticationservice.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/api/v1") @Tag(name = "auth", description = "the auth API")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
    
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    /**
     * POST /auth/login : Login and get a token which is valid for 4h
     *
     * @return Successful logged in (status code 200)
     * or Invalid username/password supplied (status code 400)
     */

    @Operation(operationId = "loginOrder", summary = "Login with orderId and lastName ", tags = {"auth"}, responses = {
            @ApiResponse(responseCode = "200", description = "Login",
                    content = {@Content(mediaType = "application/json", examples = {@ExampleObject(value = """
                            {
                              "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJDYXIgUmVudGFsIEFwcGxpY2F0aW9uIiwiZXhwIjoxNjQ5MjA2MjY0LCJpYXQiOjE2NDkxOTE4NjQsImVtYWlsIjoiYWRtaW5AYWRtaW4uaW8ifQ.b7jWKmX5eWPTBGB8Bbv5EwD25twMr5oPiGMIZP5XMGo"
                            }
                            """)}, schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid username/password supplied", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})
    
    @PostMapping(value = "/auth/order", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<AuthenticationResponse> loginOrder(@RequestBody LoginOrderRequest loginOrderRequest) {
        logger.info("Processing loginOrder Request: {}", loginOrderRequest);
        return ResponseEntity.ok(new AuthenticationResponse(
                authenticationService.loginOrder(loginOrderRequest.getId(), loginOrderRequest.getLastName())));
    }
    
    @Operation(operationId = "loginAdmin", summary = "Login with email and password", tags = {"auth"}, responses = {
            @ApiResponse(responseCode = "200", description = "Login",
                    content = {@Content(mediaType = "application/json", examples = {@ExampleObject(value = """
                            {
                              "token": "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJVc2VyIERldGFpbHMiLCJpc3MiOiJDYXIgUmVudGFsIEFwcGxpY2F0aW9uIiwiZXhwIjoxNjQ5MjA2MjY0LCJpYXQiOjE2NDkxOTE4NjQsImVtYWlsIjoiYWRtaW5AYWRtaW4uaW8ifQ.b7jWKmX5eWPTBGB8Bbv5EwD25twMr5oPiGMIZP5XMGo"
                            }
                            """)}, schema = @Schema(implementation = AuthenticationResponse.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid username/password supplied", content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))})})
    
    @PostMapping(value = "/auth/admin", produces = {"application/json"}, consumes = {"application/json"})
    public ResponseEntity<AuthenticationResponse> loginAdmin(@RequestBody LoginAdminRequest loginAdminRequest) {
        return ResponseEntity.ok(new AuthenticationResponse(
                authenticationService.loginAdmin(loginAdminRequest.getEmail(), loginAdminRequest.getPassword())));
    }
}