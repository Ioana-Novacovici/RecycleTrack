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
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@Tag(name = "Collection Controller", description = "API for collection related operations")
public class CollectionController {

    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @Operation(summary = "Add a collection")
    @PostMapping
    public ResponseEntity<?> addCollection(@RequestBody @Valid AgentCollectionDTO collectionDTO) {
        collectionService.addCollection(collectionDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all collections of a user")
    @GetMapping()
    public ResponseEntity<List<UserCollectionDTO>> getCollectionBy(@RequestParam String username) {
        var userCollections = collectionService.getCollections(username);
        return new ResponseEntity<>(userCollections, HttpStatus.OK);
    }

    @Operation(summary = "Use Points of a Collection")
    @PostMapping("/use")
    public ResponseEntity<?> useCollectionPoints(@RequestBody @Valid UserCollectionDTO collectionDTO) {
        collectionService.useCollectionPoints(collectionDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Get weekly collections")
    @GetMapping("/weekly")
    public ResponseEntity<List<UserCollectionDTO>> getWeeklyCollections() {
        var collections = collectionService.getWeeklyCollections();
        return new ResponseEntity<>(collections, HttpStatus.OK);
    }
}
