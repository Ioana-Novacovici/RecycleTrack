package com.GreenCycleSolutions.gcsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class AddressDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The street name must contain letters only")
    @Schema(example = "Marte")
    private String street;

    @NotNull
    @Schema(description = "The street number", example = "12")
    private Integer streetNumber;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9]*$", message = "The block must contain uppercase letters and digits only")
    @Schema(description = "Block name(is optional)", example = "A3")
    private String block;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9 ]*$", message = "The entrance must contain uppercase letters and digits only")
    @Schema(description = "Block entrance name(is optional)", example = "2")
    private String entrance;

    @NotNull
    @Schema(description = "Apartment number(is optional)", example = "10")
    private Integer apartmentNumber;
}
