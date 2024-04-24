package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.converter.UserConverter;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserService(UserRepository userRepository, UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }

    public void addUser(UserDTO userDTO) {
        //TODO: generate username and password for user
        userRepository.save(userConverter.convertToUserEntity(userDTO));
    }

    public UserDTO getUserById(Integer id) {
        Optional<UserEntity> user = userRepository.findById(id);
        if(user.isPresent()) {
            return UserConverter.convertToUserDTO(user.get());
        } else {
            throw new ResourceNotFoundException("User with id: " + id + " not found");
        }
    }

    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream().map(UserConverter::convertToUserDTO).toList();
    }
}
