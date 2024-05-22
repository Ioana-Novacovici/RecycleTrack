package com.GreenCycleSolutions.gcsbackend.converter;

import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public static UserDTO convertToUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .cnp(userEntity.getCnp())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .gender(userEntity.getGender())
                .build();
    }

    public UserEntity convertToUserEntity(UserDTO userDTO) {
        return UserEntity.builder()
                .cnp(userDTO.getCnp())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .gender(userDTO.getGender())
                .build();
    }
}
