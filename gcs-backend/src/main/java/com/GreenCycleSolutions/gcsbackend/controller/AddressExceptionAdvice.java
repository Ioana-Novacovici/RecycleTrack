package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.exception.AddressNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AddressExceptionAdvice {

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
    public ResponseEntity<Object> handleException(AddressNotFoundException e){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDTO(e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<Object> handleException(Exception e){
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorDTO(e.getMessage()));
    }
}
