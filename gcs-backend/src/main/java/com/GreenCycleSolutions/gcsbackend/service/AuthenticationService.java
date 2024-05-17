package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.AuthRequestDTO;
import com.GreenCycleSolutions.gcsbackend.dto.AuthenticationResponse;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.TokenEntity;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.TokenType;
import com.GreenCycleSolutions.gcsbackend.exception.AuthenticationException;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.TokenRepository;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final AccountGenerationService accountGenerationService;
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public AuthenticationService(AuthenticationManager authenticationManager, AccountGenerationService accountGenerationService, UserRepository userRepository, TokenRepository tokenRepository, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.accountGenerationService = accountGenerationService;
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.jwtService = jwtService;
    }

    public void generateAccount(UserDTO userDTO) {
        accountGenerationService.generateAccount(userDTO);
    }

    private void revokeUserTokens(UserEntity user) {
        var validUserTokens = tokenRepository.findAllByUserIdAndExpiredFalse(user.getId());
        if(validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> token.setExpired(true));
        tokenRepository.saveAll(validUserTokens);
    }

    public AuthenticationResponse login(AuthRequestDTO authRequestDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword())
        );
        var user = userRepository.findByUsername(authRequestDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("The username" + authRequestDTO.getUsername() + "was not found"));
        var jwtToken = jwtService.generateToken(Map.of("gender", user.getGender(), "role", user.getRole()), user);
        revokeUserTokens(user);
        var dbJwtToken = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .build();
        tokenRepository.save(dbJwtToken);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public String logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthenticationException("The user is already logged out");
        }
        String jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElseThrow(() -> new AuthenticationException("Invalid token"));
        if (!storedToken.isExpired()) {
            storedToken.setExpired(true);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
            return "Logout successful";
        } else {
            throw new AuthenticationException("Expired token");
        }

    }

    public String changeUsername(String newUsername, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthenticationException("The user is not logged in, hence can not change the username.");
        }
        String jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElseThrow(() -> new AuthenticationException("Invalid token"));
        if (!storedToken.isExpired()) {
            //after changing the username, automatically log out user
            storedToken.setExpired(true);
            accountGenerationService.changeUsername(storedToken.getUser().getUsername(), newUsername);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
            return "Username changed successfully";
        } else {
            throw new AuthenticationException("Expired token");
        }
    }

    public String changePassword(String newPassword, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new AuthenticationException("The user is not logged in, hence can not change the password.");
        }
        String jwt = authHeader.substring(7);
        var storedToken = tokenRepository.findByToken(jwt).orElseThrow(() -> new AuthenticationException("Invalid token"));
        if (!storedToken.isExpired()) {
            //after changing the password, automatically log out user
            storedToken.setExpired(true);
            accountGenerationService.changePassword(storedToken.getUser(), newPassword);
            tokenRepository.save(storedToken);
            SecurityContextHolder.clearContext();
            return "Password changed successfully";
        } else {
            throw new AuthenticationException("Expired token");
        }
    }
}
