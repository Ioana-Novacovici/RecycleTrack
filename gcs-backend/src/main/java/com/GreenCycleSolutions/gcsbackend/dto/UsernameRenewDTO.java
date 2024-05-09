package com.GreenCycleSolutions.gcsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UsernameRenewDTO {

    @NotBlank(message = "Username must not be null or blank")
    private String oldUsername;

    @NotBlank(message = "Username must not be null or blank")
    @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9_]*$",
            message = "Username must contain letters and optionally digits and underscore")
    private String newUsername;
}
