package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.*;
import com.GreenCycleSolutions.gcsbackend.exception.AuthenticationException;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AccountGenerationService accountGenerationService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, AccountGenerationService accountGenerationService, UserRepository userRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.accountGenerationService = accountGenerationService;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public void generateAccount(UserDTO userDTO) {
        accountGenerationService.generateAccount(userDTO);
    }

    public AuthenticationResponse login(AuthRequestDTO authRequestDTO) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        var user = userRepository.findByUsername(authRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("The username" + authRequestDTO.getUsername() + "was not found"));;
        var jwtToken = jwtService.generateToken(Map.of("gender", user.getGender(), "role", user.getRole()), user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        } else {
            throw new AuthenticationException("Can not log out user if it is not logged in.");
        }
    }

    public void changeUsername(UsernameRenewDTO usernameRenewDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null) {
            accountGenerationService.changeUsername(usernameRenewDTO.getOldUsername(), usernameRenewDTO.getNewUsername());
            session.invalidate();
        } else {
            throw new AuthenticationException("The user is not logged in, hence can not change the username.");
        }
    }

    public void changePassword(PasswordRenewDTO passwordRenewDTO, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null) {
            accountGenerationService.changePassword(passwordRenewDTO.getUsername(), passwordRenewDTO.getNewPassword());
            session.invalidate();
        } else {
            throw new AuthenticationException("The user is not logged in, hence can not change password.");
        }
    }
}
