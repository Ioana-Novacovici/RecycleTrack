package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.AuthRequestDTO;
import com.GreenCycleSolutions.gcsbackend.dto.PasswordRenewDTO;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.dto.UsernameRenewDTO;
import com.GreenCycleSolutions.gcsbackend.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AccountGenerationService accountGenerationService;
    private final UserDetailsService userDetailsService;

    public AuthenticationService(AuthenticationManager authenticationManager, AccountGenerationService accountGenerationService, UserDetailsService userDetailsService ) {
        this.authenticationManager = authenticationManager;
        this.accountGenerationService = accountGenerationService;
        this.userDetailsService = userDetailsService;
    }

    public void generateAccount(UserDTO userDTO) {
        accountGenerationService.generateAccount(userDTO);
    }

    public UserDetails login(AuthRequestDTO authRequestDTO, HttpServletRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequestDTO.getUsername());
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userDetails);
        return userDetails;
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
