package com.GreenCycleSolutions.gcsbackend.service;


import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public List<AddressDTO> getAllAddresses() {
        return addressRepository.findAll()
                .stream()
                .map(AddressService::convertToAddressDTO)
                .collect(Collectors.toList());
    }

    public void addAddress(AddressDTO addressDTO) {
        addressRepository.save(convertToAddressEntity(addressDTO));
    }

    private static AddressEntity convertToAddressEntity(AddressDTO addressDTO) {
        return AddressEntity.builder()
                .street(addressDTO.getStreet())
                .streetNumber(addressDTO.getStreetNumber())
                .block(addressDTO.getBlock())
                .entrance(addressDTO.getEntrance())
                .apartmentNumber(addressDTO.getApartmentNumber())
                .build();
    }

    private static AddressDTO convertToAddressDTO(AddressEntity addressEntity) {
        return AddressDTO.builder()
                .street(addressEntity.getStreet())
                .streetNumber(addressEntity.getStreetNumber())
                .block(addressEntity.getBlock())
                .entrance(addressEntity.getEntrance())
                .apartmentNumber(addressEntity.getApartmentNumber())
                .build();
    }
}
