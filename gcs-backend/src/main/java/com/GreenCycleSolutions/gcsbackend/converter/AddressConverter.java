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
                .entrance(addressDTO.getEntrance())
                .apartmentNumber(addressDTO.getApartmentNumber())
                .user(userEntity)
                .build();
    }

    public static AddressDTO convertToAddressDTO(AddressEntity addressEntity) {
        return AddressDTO.builder()
                .street(addressEntity.getStreet())
                .streetNumber(addressEntity.getStreetNumber())
                .block(addressEntity.getBlock())
                .entrance(addressEntity.getEntrance())
                .apartmentNumber(addressEntity.getApartmentNumber())
                .userId(addressEntity.getUser().getId())
                .build();
    }
}
