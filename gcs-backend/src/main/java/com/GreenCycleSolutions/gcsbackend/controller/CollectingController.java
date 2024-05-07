package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.dto.CollectingDTO;
import com.GreenCycleSolutions.gcsbackend.service.CollectingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/collecting")
@Tag(name = "Collecting Controller", description = "API for collecting related operations")
public class CollectingController {

    private final CollectingService collectingService;

    public CollectingController(CollectingService collectingService) {
        this.collectingService = collectingService;
    }

    @Operation(summary = "Add a collecting")
    @PostMapping
    public ResponseEntity<?> addCollecting(@Valid @RequestBody CollectingDTO collectingDTO) {
        collectingService.addCollecting(collectingDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
