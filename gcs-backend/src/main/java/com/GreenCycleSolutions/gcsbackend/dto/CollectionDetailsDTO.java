package com.GreenCycleSolutions.gcsbackend.dto;

import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CollectionDetailsDTO {

    private Double quantity;
    private Integer points;
    private RecycledType recycledType;
}
