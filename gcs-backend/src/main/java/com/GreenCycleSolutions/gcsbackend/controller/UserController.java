package com.GreenCycleSolutions.gcsbackend.controller;


import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> addUser(@Valid @RequestBody UserDTO userDTO) {
        userService.addUser(userDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get an user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable @NotNull Integer id) {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }
}
