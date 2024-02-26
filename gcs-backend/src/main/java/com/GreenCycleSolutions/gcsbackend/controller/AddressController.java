package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @GetMapping()
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @PostMapping
    public ResponseEntity<?> addAddress(@Valid @RequestBody AddressDTO addressDTO) {
        addressService.addAddress(addressDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
