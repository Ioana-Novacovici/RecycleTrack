package com.GreenCycleSolutions.gcsbackend.controller;


import com.GreenCycleSolutions.gcsbackend.dto.*;
import com.GreenCycleSolutions.gcsbackend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping(value = "/auth")
@Tag(name = "Authentication Controller", description = "API for user accounts related operations")
public class AuthenticationController {


    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Account activation request ")
    @PostMapping("/account")
    public ResponseEntity<?> addAccount(@RequestBody @Valid UserDTO userDTO) {
        authenticationService.generateAccount(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "User log in")
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthRequestDTO authRequestDTO) {
        var response = authenticationService.login(authRequestDTO);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "User log out")
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
       return new ResponseEntity<>(authenticationService.logout(request), HttpStatus.OK);
    }

    @Operation(summary = "Change username of the logged user")
    @PostMapping("/username")
    public ResponseEntity<String> changeUsername(@RequestBody String newUsername, HttpServletRequest request) {
        return new ResponseEntity<>(authenticationService.changeUsername(newUsername, request), HttpStatus.OK);
    }

    @Operation(summary = "Change the password of the logged user")
    @PostMapping("/password")
    public ResponseEntity<String> changePassword(@RequestBody String newPassword, HttpServletRequest request) {
        return new ResponseEntity<>(authenticationService.changePassword(newPassword, request), HttpStatus.OK);
    }
}
