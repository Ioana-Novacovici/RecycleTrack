package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserDTO userDTO) {
        //TODO: generate username and password for user, add them to dto
        userRepository.save(convertToUserEntity(userDTO));
    }

    private UserDTO convertToUserDTO(UserEntity userEntity) {
        //TODO: check if username and password are necessary in dto
        return UserDTO.builder()
                .cnp(userEntity.getCnp())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .build();
    }

    private UserEntity convertToUserEntity(UserDTO userDTO) {
        //TODO: encrypt password before storing it into DB
        //TODO: encrypt cnp before storing to DB
        return UserEntity.builder()
                .cnp(userDTO.getCnp())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(userDTO.getEmail())
                .role(userDTO.getRole())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .build();
    }
}
