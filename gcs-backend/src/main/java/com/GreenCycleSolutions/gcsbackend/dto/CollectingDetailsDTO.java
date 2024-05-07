package com.GreenCycleSolutions.gcsbackend.dto;

import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectingDetailsDTO {

    private Double kilograms;
    private Integer points;
    private RecycledType recycledType;
}
