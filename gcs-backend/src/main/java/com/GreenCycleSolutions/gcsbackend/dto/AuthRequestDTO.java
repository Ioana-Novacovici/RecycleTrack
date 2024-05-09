package com.GreenCycleSolutions.gcsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDTO {

    @NotBlank(message = "Username must not be null or blank")
    private String username;

    @NotBlank(message = "Password must not be null or blank")
    private String password;
}
