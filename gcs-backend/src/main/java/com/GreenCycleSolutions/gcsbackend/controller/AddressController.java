package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/addresses")
@Tag(name = "Address Controller", description = "API for address related operations")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }


    @Operation(summary = "Gets all addresses",
            description = "Returns a list of all existing addresses")
    @GetMapping()
    public List<AddressDTO> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @Operation(summary = "Add a new address")
    @PostMapping
    public ResponseEntity<?> addAddress(@Valid @RequestBody  AddressDTO addressDTO) {
        addressService.addAddress(addressDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
