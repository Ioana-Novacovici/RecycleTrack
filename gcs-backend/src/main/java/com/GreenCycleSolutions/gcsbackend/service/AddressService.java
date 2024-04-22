package com.GreenCycleSolutions.gcsbackend.service;


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
            return convertToAddressDTO(address.get());
        } else {
            throw new ResourceNotFoundException("Address with id: " + id + " not found");
        }
    }

    public List<AddressDTO> findAddressBy(String street, Integer number, String block, String entrance, Integer apartmentNumber, Integer userId) {
        List<AddressEntity> addresses = addressRepository.findAllBy(street, number, block, entrance, apartmentNumber, userId);
        if(!addresses.isEmpty()) {
            return addresses.stream()
                    .map(AddressService::convertToAddressDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("No address found based on filter");
        }

    }

    //TODO: retrieve here all addresses of a user findAddressesBy(Integer userId)

    public void addAddress(AddressDTO addressDTO) {
        Optional<UserEntity> userEntity = userRepository.findById(addressDTO.getUserId());
        if(userEntity.isPresent()) {
            addressRepository.save(convertToAddressEntity(addressDTO, userEntity.get()));
        } else {
            throw new ResourceNotFoundException("The user with id " + addressDTO.getUserId() + " does not exist");
        }
    }


    private static AddressEntity convertToAddressEntity(AddressDTO addressDTO, UserEntity userEntity) {
        return AddressEntity.builder()
                .street(addressDTO.getStreet())
                .streetNumber(addressDTO.getStreetNumber())
                .block(addressDTO.getBlock())
                .entrance(addressDTO.getEntrance())
                .apartmentNumber(addressDTO.getApartmentNumber())
                .user(userEntity)
                .build();
    }

    private static AddressDTO convertToAddressDTO(AddressEntity addressEntity) {
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
