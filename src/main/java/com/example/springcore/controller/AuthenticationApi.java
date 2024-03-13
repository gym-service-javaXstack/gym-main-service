package com.example.springcore.controller;


import com.example.springcore.dto.NewPasswordDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Authentication service", description = "Authentication management API")
@RequestMapping("/api/v1/login")
public interface AuthenticationApi {

    @Operation(summary = "Log in with credentials")
    @Parameters({
            @Parameter(name = "username", description = "Username for login", required = true),
            @Parameter(name = "password", description = "Password for login", required = true)
    })
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @GetMapping
    ResponseEntity<Void> loginUserWithCredentials(@RequestParam String username, @RequestParam String password);

    @Operation(summary = "Change login with new password")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "New password details", required = true,
            content = @Content(schema = @Schema(implementation = NewPasswordDTO.class)))
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @PutMapping
    ResponseEntity<Void> changeLoginWithNewPassword(@Valid @RequestBody NewPasswordDTO newPasswordDTORequest);
}
