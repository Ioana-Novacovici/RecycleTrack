package com.GreenCycleSolutions.gcsbackend.dto;

import com.GreenCycleSolutions.gcsbackend.entity.enumtype.Gender;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    @NotBlank(message = "CNP must not be null or blank")
    @Pattern(regexp = "^\\d{13}$", message = "Must be a valid cnp")
    @Schema(example = "1012304789004")
    private String cnp;

    @NotBlank(message = "First name must not be null or blank")
    @Pattern(regexp = "^[A-Z][a-zA-Z- ]+$", message = "Must be a valid first name")
    @Schema(example = "Maria")
    private String firstName;

    @NotBlank(message = "First name must not be null or blank")
    @Pattern(regexp = "^[A-Z][a-zA-Z- ]+$", message = "Must be a valid last name")
    @Schema(example = "Pop")
    private String lastName;

    @Email(message = "Must be a valid email address")
    @Schema(example = "maria.popescu96@gmail.com")
    @Nullable
    private String email;

    @Schema(example = "USER")
    private Role role;

    @Schema(example = "FEMALE")
    private Gender gender;
}
