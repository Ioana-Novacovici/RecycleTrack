package com.GreenCycleSolutions.gcsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    @NotBlank(message = "First name must not be null or blank")
    @Pattern(regexp = "^[a-zA-Z- ]+$", message = "Must be a valid first name")
    @Schema(example = "Maria")
    private String firstName;

    @NotBlank(message = "First name must not be null or blank")
    @Pattern(regexp = "^[a-zA-Z- ]+$", message = "Must be a valid last name")
    @Schema(example = "Pop")
    private String lastName;

    @Email(message = "Must be a valid email address")
    @Schema(example = "maria.popescu96@gmail.com")
    private String email;

}
