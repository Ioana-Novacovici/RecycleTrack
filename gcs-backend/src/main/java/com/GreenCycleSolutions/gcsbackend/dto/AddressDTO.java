package com.GreenCycleSolutions.gcsbackend.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AddressDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z -]+$")
    private String street;

    @NotNull
    private Integer streetNumber;

    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String block;

    @Pattern(regexp = "^[a-zA-Z0-9]+$")
    private String entrance;

    private Integer apartmentNumber;
}
