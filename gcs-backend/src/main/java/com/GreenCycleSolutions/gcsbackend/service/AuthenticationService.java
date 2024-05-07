package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.AuthRequest;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
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

    public AuthenticationService(AuthenticationManager authenticationManager, AccountGenerationService accountGenerationService, UserDetailsService userDetailsService) {
        this.authenticationManager = authenticationManager;
        this.accountGenerationService = accountGenerationService;
        this.userDetailsService = userDetailsService;
    }

    public void generateAccount(UserDTO userDTO) {
        accountGenerationService.generateAccount(userDTO);
    }

    public UserDetails login(AuthRequest authRequest, HttpServletRequest request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());
        HttpSession session = request.getSession(true);
        session.setAttribute("user", userDetails);
        return userDetails;
    }

    public void logout(HttpServletRequest request) {
        //TODO check here the getSession(it should have false as param - to use the session with the logged in user)
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
        } else {
            throw new AuthenticationException("Can not log out user if it is not logged in.");
        }
    }

    public void changeUsername(String newUsername, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            UserDetails userDetails = (UserDetails) session.getAttribute("user");
            accountGenerationService.changeUsername(userDetails.getUsername(), newUsername);
            session.invalidate();
        } else {
            throw new AuthenticationException("The user is not logged in, hence can not change the username.");
        }
    }

    public void changePassword(String newPassword, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session != null) {
            UserDetails userDetails = (UserDetails) session.getAttribute("user");
            accountGenerationService.changePassword(userDetails.getUsername(), newPassword);
            session.invalidate();
        } else {
            throw new AuthenticationException("The user is not logged in, hence can not change password.");
        }
    }
}
