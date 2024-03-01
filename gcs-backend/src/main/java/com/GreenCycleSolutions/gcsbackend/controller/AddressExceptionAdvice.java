package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.exception.AddressAlreadyExistsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.sql.SQLIntegrityConstraintViolationException;

@ControllerAdvice
public class AddressExceptionAdvice {

    record ErrorDTO(String message) {
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(SQLIntegrityConstraintViolationException e){
        return ResponseEntity.badRequest().body(new ErrorDTO(new AddressAlreadyExistsException().getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException e){
        return ResponseEntity.badRequest().body(new ErrorDTO(e.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }
}
