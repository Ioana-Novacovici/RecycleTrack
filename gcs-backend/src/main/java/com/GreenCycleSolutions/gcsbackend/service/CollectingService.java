package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.CollectingDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectingDetailsEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectingEntity;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectingDetailsRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@Service
public class CollectingService {

    private final CollectingDetailsRepository collectingDetailsRepository;
    private final CollectingRepository collectingRepository;
    private final AddressRepository addressRepository;

    public CollectingService(CollectingDetailsRepository collectingDetailsRepository, CollectingRepository collectingRepository, AddressRepository addressRepository) {
        this.collectingDetailsRepository = collectingDetailsRepository;
        this.collectingRepository = collectingRepository;
        this.addressRepository = addressRepository;
    }

    public void addCollecting(CollectingDTO collectingDTO) {
        Optional<AddressEntity> addressEntityOptional = addressRepository
                .findByCollectingCode(collectingDTO.getCollectingCode());
        if (addressEntityOptional.isPresent()) {
            var address = addressEntityOptional.get();
            CollectingEntity collecting = collectingRepository.save(CollectingEntity.builder()
                    .date(LocalDate.now())
                    .addressEntity(address)
                    .build());
            for (Map.Entry<RecycledType, Double> entry : collectingDTO.getQuantities().entrySet()) {
                collectingDetailsRepository.save(CollectingDetailsEntity.builder()
                        .collectingEntity(collecting)
                                .recycledType(entry.getKey())
                                .kilograms(entry.getValue())
                                .points(100)
                        .build());
            }

        } else {
            throw new ResourceNotFoundException("The collecting code provided is not correct");
        }
    }
}
