package com.GreenCycleSolutions.gcsbackend.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class UserCollectingDTO {

    private LocalDate date;
    private Integer totalPoints;
    private Double totalQuantity;
    private List<CollectingDetailsDTO> collectingDetailsDTOList;

}
