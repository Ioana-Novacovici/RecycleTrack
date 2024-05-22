package com.GreenCycleSolutions.gcsbackend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class PasswordRenewDTO {

    @NotBlank(message = "Password must not be null or blank")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{6,}$",
            message = "Password must contain letters, at least one digit and one special character")
    @Length(min = 6, message = "Password must have minimum 6 characters")
    private String newPassword;

}
