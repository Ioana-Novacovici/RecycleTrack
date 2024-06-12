package com.GreenCycleSolutions.gcsbackend.service;


import com.GreenCycleSolutions.gcsbackend.converter.AddressConverter;
import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.UserEntity;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.Role;
import com.GreenCycleSolutions.gcsbackend.exception.NotSupportedOperation;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    public AddressDTO findById(Integer id) {
        var address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address with id: " + id + " not found"));
        return AddressConverter.convertToAddressDTO(address);
    }

    public List<AddressDTO> findAddressBy(String street, Integer number, String block, Integer apartmentNumber, String username) {
        List<AddressEntity> addresses = addressRepository.findAllBy(street, number, block, apartmentNumber, username);
        if(!addresses.isEmpty()) {
            return addresses.stream()
                    .map(AddressConverter::convertToAddressDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("No address found based on filter");
        }
    }

    public List<AddressDTO> getAddressesFromCurrentDay() {
        DayOfWeek currentDay = LocalDate.now().getDayOfWeek();
        List<AddressEntity> addresses = addressRepository.findAddressEntitiesByDayEquals(currentDay);
        if(!addresses.isEmpty()) {
            return addresses.stream()
                    .map(AddressConverter::convertToAddressDTO)
                    .collect(Collectors.toList());
        } else {
            throw new ResourceNotFoundException("No address scheduled for this day!");
        }
    }

    public void addAddress(AddressDTO addressDTO) {
        var user = userRepository.findById(addressDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("The user with id " + addressDTO.getId() + " does not exist"));
        if(Objects.equals(user.getRole(), Role.USER)) {
            //generate the unique code for this address
            addressDTO.setCollectionCode(UUID.randomUUID());
            addressRepository.save(AddressConverter.convertToAddressEntity(addressDTO, user));
        } else {
            throw new NotSupportedOperation("Can not link address to other roles beside USER yet.");
        }

    }
}
