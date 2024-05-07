package com.GreenCycleSolutions.gcsbackend.converter;

import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;

public class AddressConverter {

    public static AddressEntity convertToAddressEntity(AddressDTO addressDTO, UserEntity userEntity) {
        return AddressEntity.builder()
                .street(addressDTO.getStreet())
                .streetNumber(addressDTO.getStreetNumber())
                .block(addressDTO.getBlock())
                .apartmentNumber(addressDTO.getApartmentNumber())
                .collectingCode(addressDTO.getCollectingCode())
                .day(addressDTO.getDay())
                .user(userEntity)
                .build();
    }

    public static AddressDTO convertToAddressDTO(AddressEntity addressEntity) {
        return AddressDTO.builder()
                .street(addressEntity.getStreet())
                .streetNumber(addressEntity.getStreetNumber())
                .block(addressEntity.getBlock())
                .apartmentNumber(addressEntity.getApartmentNumber())
                .collectingCode(addressEntity.getCollectingCode())
                .id(addressEntity.getUser().getId())
                .day(addressEntity.getDay())
                .build();
    }
}
