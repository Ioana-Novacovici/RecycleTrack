package com.GreenCycleSolutions.gcsbackend.dto;


import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class AgentCollectionDTO {

    @NotNull(message = "Collection must have an identification code")
    private UUID collectionCode;

    @NotNull(message = "A collection must contain quantities of material collected")
    private Map<RecycledType, Double> quantities;
}
