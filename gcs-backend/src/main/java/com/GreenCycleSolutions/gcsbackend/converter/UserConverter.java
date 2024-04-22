package com.GreenCycleSolutions.gcsbackend.converter;

import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;

public class UserConverter {

    public static UserDTO convertToUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .cnp(userEntity.getCnp())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }

    public static UserEntity convertToUserEntity(UserDTO userDTO) {
        //TODO: encrypt password & cnp before storing it into DB
        //TODO: encrypt cnp before storing to DB
        return UserEntity.builder()
                .cnp(userDTO.getCnp())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .build();
    }
}
