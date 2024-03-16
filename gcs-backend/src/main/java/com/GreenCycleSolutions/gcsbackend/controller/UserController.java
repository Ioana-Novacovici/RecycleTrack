package com.GreenCycleSolutions.gcsbackend.controller;


import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/users")
@Tag(name = "User Controller", description = "API for user related operations")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Add a new user")
    @PostMapping
    public ResponseEntity<?> addAddress(@Valid @RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
