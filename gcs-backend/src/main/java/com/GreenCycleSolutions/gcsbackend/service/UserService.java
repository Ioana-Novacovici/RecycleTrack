package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(UserDTO userDTO) {
        //TODO: generate username and password for user
        userRepository.save(convertToUserEntity(userDTO));
    }

    public UserDTO getUserById(Integer id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) {
            return convertToUserDTO(user.get());
        } else {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
    }

    private UserDTO convertToUserDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .cnp(userEntity.getCnp())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
    }

    private UserEntity convertToUserEntity(UserDTO userDTO) {
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
