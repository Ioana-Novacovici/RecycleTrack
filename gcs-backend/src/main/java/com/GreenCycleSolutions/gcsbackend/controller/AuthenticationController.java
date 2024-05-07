package com.GreenCycleSolutions.gcsbackend.controller;


import com.GreenCycleSolutions.gcsbackend.dto.AuthRequest;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.exception.AuthenticationException;
import com.GreenCycleSolutions.gcsbackend.service.AccountGenerationService;
import com.GreenCycleSolutions.gcsbackend.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        var userDetails = authenticationService.login(authRequest, request);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @Operation(summary = "User log out")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
       authenticationService.logout(request);
       return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Change username of the logged user")
    @PostMapping("/username")
    public ResponseEntity<?> changeUsername(@RequestBody String newUsername, HttpServletRequest request) {
        authenticationService.changeUsername(newUsername, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Change the password of the logged user")
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody String newPassword, HttpServletRequest request) {
        authenticationService.changePassword(newPassword, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
