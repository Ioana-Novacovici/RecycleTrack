package com.GreenCycleSolutions.gcsbackend.controller;

import com.GreenCycleSolutions.gcsbackend.dto.UserCollectingDTO;
import com.GreenCycleSolutions.gcsbackend.dto.AgentCollectingDTO;
import com.GreenCycleSolutions.gcsbackend.service.CollectingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<?> addCollecting(@Valid @RequestBody AgentCollectingDTO collectingDTO) {
        collectingService.addCollecting(collectingDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "Get all collectings of a user")
    @GetMapping()
    public ResponseEntity<List<UserCollectingDTO>> getCollectingBy(@RequestParam(required = false) String username) {
        var userCollectings = collectingService.getCollectings(username);
        return new ResponseEntity<>(userCollectings, HttpStatus.OK);
    }
}
