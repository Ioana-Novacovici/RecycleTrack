package com.GreenCycleSolutions.gcsbackend.service;


import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.exception.AddressNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AddressService {

    private final AddressRepository addressRepository;

    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    public AddressDTO findById(Integer id) {
        Optional<AddressEntity> address = addressRepository.findById(id);
        if(address.isPresent()){
            return convertToAddressDTO(address.get());
        } else{
            throw new AddressNotFoundException();
        }
    }

    public List<AddressDTO> findAddressBy(String street, Integer number, String block, String entrance, Integer apartmentNumber) {
        List<AddressEntity> addresses = addressRepository.findAllBy(street, number, block, entrance, apartmentNumber);
        if(!addresses.isEmpty()){
            return addresses.stream()
                    .map(AddressService::convertToAddressDTO)
                    .collect(Collectors.toList());
        } else {
            throw new AddressNotFoundException();
        }

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
