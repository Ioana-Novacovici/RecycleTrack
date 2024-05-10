package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.dto.UserCollectionDTO;
import com.GreenCycleSolutions.gcsbackend.dto.AgentCollectionDTO;
import com.GreenCycleSolutions.gcsbackend.service.CollectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/collections")
@Tag(name = "Collection Controller", description = "API for collection related operations")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Operation(summary = "Add a collection")
    @PostMapping
    public ResponseEntity<?> addCollection(@Valid @RequestBody AgentCollectionDTO collectionDTO) {
        collectionService.addCollection(collectionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all collections of a user")
    @GetMapping()
    public ResponseEntity<List<UserCollectionDTO>> getCollectionBy(@RequestParam(required = false) String username) {
        var userCollections = collectionService.getCollections(username);
        return new ResponseEntity<>(userCollections, HttpStatus.OK);
    }
}
