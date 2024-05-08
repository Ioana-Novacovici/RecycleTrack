package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.AuthRequest;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.dto.UsernameRequest;
import com.GreenCycleSolutions.gcsbackend.exception.AuthenticationException;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
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
    private final UserRepository userRepository;

    public AuthenticationService(AuthenticationManager authenticationManager, AccountGenerationService accountGenerationService, UserDetailsService userDetailsService, UserRepository userRepository) {
        this.authenticationManager = authenticationManager;
        this.accountGenerationService = accountGenerationService;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
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

    public void changeUsername(UsernameRequest usernameRequest, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null) {
            accountGenerationService.changeUsername(usernameRequest.getOldUsername(), usernameRequest.getNewUsername());
            session.invalidate();
        } else {
            throw new AuthenticationException("The user is not logged in, hence can not change the username.");
        }
    }

    public void changePassword(AuthRequest authRequest, HttpServletRequest request) {
        HttpSession session = request.getSession();
        if(session != null) {
            accountGenerationService.changePassword(authRequest.getUsername(), authRequest.getPassword());
            session.invalidate();
        } else {
            throw new AuthenticationException("The user is not logged in, hence can not change password.");
        }
    }
}
