package com.GreenCycleSolutions.gcsbackend.dto;


import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class CollectingDTO {

    @NotNull(message = "Collecting must have an identification code")
    private UUID collectingCode;

    @NotNull(message = "A collecting must contain quantities of material collected")
    private Map<RecycledType, Double> quantities;
}
