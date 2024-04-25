package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.dto.AddressDTO;
import com.GreenCycleSolutions.gcsbackend.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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

    @Operation(summary = "Get an address by id")
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable @NotNull Integer id) {
        return new ResponseEntity<>(addressService.findById(id), HttpStatus.OK);
    }

    @Operation(summary = "Search for certain address based on filter")
    @GetMapping
    public ResponseEntity<List<AddressDTO>> findAddress(
            @RequestParam(required = false) String street,
            @RequestParam(required = false) Integer number,
            @RequestParam(required = false) String block,
            @RequestParam(required = false) String entrance,
            @RequestParam(required = false) Integer apartmentNumber,
            @RequestParam(required = false) String username) {
        List<AddressDTO> addressDTOs = addressService
                .findAddressBy(street, number, block, entrance, apartmentNumber, username);
        return new ResponseEntity<>(addressDTOs, HttpStatus.OK);
    }

    @Operation(summary = "Add an address")
    @PostMapping
    public ResponseEntity<?> addAddress(@Valid @RequestBody  AddressDTO addressDTO) {
        addressService.addAddress(addressDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
