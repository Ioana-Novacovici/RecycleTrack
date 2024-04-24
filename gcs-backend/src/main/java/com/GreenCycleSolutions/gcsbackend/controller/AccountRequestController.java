package com.GreenCycleSolutions.gcsbackend.controller;


import com.GreenCycleSolutions.gcsbackend.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/account")
@Tag(name = "Account Controller", description = "API for user accounts related operations")
public class AccountRequestController {

    private final EmailService emailService;

    public AccountRequestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @Operation(summary = "Request an account")
    @PostMapping
    public ResponseEntity<?> addAccount(@RequestBody String email) {
        emailService.sendEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
