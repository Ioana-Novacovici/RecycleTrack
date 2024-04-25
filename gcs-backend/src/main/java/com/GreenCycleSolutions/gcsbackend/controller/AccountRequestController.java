package com.GreenCycleSolutions.gcsbackend.controller;


import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.service.AccountGenerationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/account")
@Tag(name = "Account Controller", description = "API for user accounts related operations")
public class AccountRequestController {

    private final AccountGenerationService accountGenerationService;

    public AccountRequestController(AccountGenerationService accountGenerationService) {
        this.accountGenerationService = accountGenerationService;
    }

    @Operation(summary = "Request an account")
    @PostMapping("/request")
    public ResponseEntity<?> addAccount(@RequestBody @Valid UserDTO userDTO) {
        accountGenerationService.generateAccount(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Request username change")
    @PostMapping("/username")
    public ResponseEntity<?> changeUsername(@RequestBody Map<String, String> usernames) {
        accountGenerationService.changeUsername(usernames.get("oldUsername"), usernames.get("newUsername"));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Request password change")
    @PostMapping("/password")
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> usernamePassword) {
        //TODO maybe create a dto for these requests
        accountGenerationService.changePassword(usernamePassword.get("username"), usernamePassword.get("newPassword"));
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
