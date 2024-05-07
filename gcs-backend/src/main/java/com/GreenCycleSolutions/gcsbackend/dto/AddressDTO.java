package com.GreenCycleSolutions.gcsbackend.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.UUID;


@Data
@Builder
public class AddressDTO {

    @NotBlank(message = "Street name must not be null or blank")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "The street name must contain letters only")
    @Schema(example = "Marte")
    private String street;

    @NotNull(message = "Street must not be null")
    @Min(value = 1)
    @Schema(description = "The street number", example = "12")
    private Integer streetNumber;

    @NotNull(message = "Block name must not be null")
    @Pattern(regexp = "^[A-Z0-9]*$", message = "The block must contain uppercase letters and digits only")
    @Schema(description = "Block name(is optional)", example = "A3")
    private String block;

    @NotNull(message = "Apartment number must not be null")
    @Schema(description = "Apartment number(is optional)", example = "10")
    private Integer apartmentNumber;

    @NotNull(message = "The address must have an owner")
    @Schema(description = "The user id, owner of the address", example = "1")
    @Min(value = 1)
    private Integer id;

    @Schema(example = "MONDAY")
    @NotNull(message = "The address must have an owner")
    private DayOfWeek day;

    private UUID collectingCode;
}
