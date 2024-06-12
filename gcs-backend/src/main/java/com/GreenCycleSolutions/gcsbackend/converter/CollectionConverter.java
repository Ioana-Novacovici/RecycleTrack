package com.GreenCycleSolutions.gcsbackend.converter;

import com.GreenCycleSolutions.gcsbackend.dto.CollectionDetailsDTO;
import com.GreenCycleSolutions.gcsbackend.dto.UserCollectionDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectionDetailsEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectionEntity;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class CollectionConverter {

    public static CollectionEntity convertToCollectionEntity(AddressEntity address) {
        return CollectionEntity.builder()
                .date(LocalDate.now())
                .address(address)
                .isUsed(false)
                .build();
    }

    public static CollectionDetailsEntity convertToCollectionDetailsEntity(Map.Entry<RecycledType, Double> entry, CollectionEntity collection) {
        return CollectionDetailsEntity.builder()
                .collectionEntity(collection)
                .recycledType(entry.getKey())
                .quantity(entry.getValue())
                .build();
    }

    public static UserCollectionDTO convertToCollectionDTO(CollectionEntity collection, List<CollectionDetailsDTO> collectionDetailsList) {
        return UserCollectionDTO.builder()
                .date(collection.getDate())
                .collectionDetailsDTOList(collectionDetailsList)
                .totalPoints(collection.getTotalPoints())
                .totalQuantity(collection.getTotalQuantity())
                .isUsed(collection.getIsUsed())
                .build();
    }

    public static UserCollectionDTO convertToCollectionDTO(CollectionEntity collection, String username) {
        return UserCollectionDTO.builder()
                .date(collection.getDate())
                .totalPoints(collection.getTotalPoints())
                .totalQuantity(collection.getTotalQuantity())
                .username(username)
                .build();
    }

    public static CollectionDetailsDTO convertToCollectionDetailsDTO(CollectionDetailsEntity collectionDetails) {
        return CollectionDetailsDTO.builder()
                .recycledType(collectionDetails.getRecycledType())
                .quantity(collectionDetails.getQuantity())
                .build();
    }

    public static Integer computePoints(RecycledType type, Double quantity) {
        return switch (type) {
            case GLASS -> (int) (quantity * 5);
            case METAL -> (int) (quantity * 6);
            case PAPER -> (int) (quantity * 7);
            case PLASTIC -> (int) (quantity * 8);
        };
    }
}
