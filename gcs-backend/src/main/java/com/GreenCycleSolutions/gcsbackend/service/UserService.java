package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.converter.UserConverter;
import com.GreenCycleSolutions.gcsbackend.dto.UserDTO;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public void addUser(UserDTO userDTO) {
        userRepository.save(userConverter.convertToUserEntity(userDTO));
    }

    public UserDTO getUserById(Integer id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));
        return UserConverter.convertToUserDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        var users = userRepository.findAll();
        return users.stream().map(UserConverter::convertToUserDTO).toList();
    }

    public String deleteUserById(Integer id) {
        userRepository.deleteById(id);
        return "User deleted successfully!";
    }
}
