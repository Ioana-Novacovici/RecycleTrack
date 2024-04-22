package com.GreenCycleSolutions.gcsbackend.service;


import com.GreenCycleSolutions.gcsbackend.converter.AddressConverter;
import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressService(AddressRepository addressRepository, UserRepository userRepository) {
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public AddressDTO findById(Integer id) {
        Optional<AddressEntity> address = addressRepository.findById(id);
        if(address.isPresent()) {
            return AddressConverter.convertToAddressDTO(address.get());
        } else {
            throw new ResourceNotFoundException("Address with id: " + id + " not found");
        }
    }

    public List<AddressDTO> findAddressBy(String street, Integer number, String block, String entrance, Integer apartmentNumber, Integer userId) {
        List<AddressEntity> addresses = addressRepository.findAllBy(street, number, block, entrance, apartmentNumber, userId);
        if(!addresses.isEmpty()) {
            return addresses.stream()
                    .map(AddressConverter::convertToAddressDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("No address found based on filter");
        }

    }

    public void addAddress(AddressDTO addressDTO) {
        Optional<UserEntity> userEntity = userRepository.findByUsername(addressDTO.getUsername());
        if(userEntity.isPresent()) {
            addressRepository.save(AddressConverter.convertToAddressEntity(addressDTO, userEntity.get()));
        } else {
            throw new ResourceNotFoundException("The user with username " + addressDTO.getUsername() + " does not exist");
        }
    }
}
