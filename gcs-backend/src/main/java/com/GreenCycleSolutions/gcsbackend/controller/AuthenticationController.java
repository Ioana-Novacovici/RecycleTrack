package com.GreenCycleSolutions.gcsbackend.controller;


import com.GreenCycleSolutions.gcsbackend.dto.AuthRequest;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.exception.AuthenticationException;
import com.GreenCycleSolutions.gcsbackend.service.AccountGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RequestMapping(value = "/auth")
@Tag(name = "Authentication Controller", description = "API for user accounts related operations")
public class AuthenticationController {

    private final AccountGenerationService accountGenerationService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;

    public AuthenticationController(AccountGenerationService accountGenerationService, AuthenticationManager authenticationManager, UserDetailsService userDetailsService) {
        this.accountGenerationService = accountGenerationService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @Operation(summary = "Account activation request ")
    @PostMapping("/account")
    public ResponseEntity<?> addAccount(@RequestBody @Valid UserDTO userDTO) {
        accountGenerationService.generateAccount(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "User log in")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest, HttpServletRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userDetails);
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @Operation(summary = "User log out")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        //TODO check here the getSession(it should have false as param - to use the session with the logged in user)
        HttpSession session = request.getSession();
         if (session != null) {
            session.invalidate();
        } else {
            throw new AuthenticationException("Can not log out user if it is not logged in.");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Change username of the logged user")
    @PostMapping("/username")
    public ResponseEntity<?> changeUsername(@RequestBody String newUsername, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            UserDetails userDetails = (UserDetails) session.getAttribute("user");
            accountGenerationService.changeUsername(userDetails.getUsername(), newUsername);
            session.invalidate();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
           throw new AuthenticationException("The user is not logged in, hence can not change the username.");
        }
    }

    @Operation(summary = "Change the password of the logged user")
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody String newPassword, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            UserDetails userDetails = (UserDetails) session.getAttribute("user");
            accountGenerationService.changePassword(userDetails.getUsername(), newPassword);
            session.invalidate();
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            throw new AuthenticationException("The user is not logged in, hence can not change password.");
        }
    }
}
