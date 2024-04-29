package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.exception.AccountGenerationException;
import com.GreenCycleSolutions.gcsbackend.exception.AuthenticationException;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    record ErrorDTO(String message) {
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(DataIntegrityViolationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(ResourceNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(AccountGenerationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler()
    public ResponseEntity<Object> handleException(AuthenticationServiceException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler()
    public ResponseEntity<Object> handleException(BadCredentialsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(AuthenticationException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
    }
}
