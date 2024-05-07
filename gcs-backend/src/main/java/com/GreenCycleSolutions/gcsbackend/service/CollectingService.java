package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.AgentCollectingDTO;
import com.GreenCycleSolutions.gcsbackend.dto.CollectingDetailsDTO;
import com.GreenCycleSolutions.gcsbackend.dto.UserCollectingDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectingDetailsEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectingEntity;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectingDetailsRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectingRepository;
import com.GreenCycleSolutions.gcsbackend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CollectingService {

    private final CollectingDetailsRepository collectingDetailsRepository;
    private final CollectingRepository collectingRepository;
    private final AddressRepository addressRepository;

    private final UserRepository userRepository;

    public CollectingService(CollectingDetailsRepository collectingDetailsRepository, CollectingRepository collectingRepository, AddressRepository addressRepository, UserRepository userRepository) {
        this.collectingDetailsRepository = collectingDetailsRepository;
        this.collectingRepository = collectingRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public void addCollecting(AgentCollectingDTO collectingDTO) {
        Optional<AddressEntity> addressEntityOptional = addressRepository
                .findByCollectingCode(collectingDTO.getCollectingCode());
        if (addressEntityOptional.isPresent()) {
            var address = addressEntityOptional.get();
            CollectingEntity collecting = collectingRepository.save(CollectingEntity.builder()
                    .date(LocalDate.now())
                    .address(address)
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

    public List<UserCollectingDTO> getCollectings(String username) {
        Optional<AddressEntity> addressEntityOptional = addressRepository.findByUserUsername(username);
        if(addressEntityOptional.isPresent()) {
            var addressId = addressEntityOptional.get().getId();
            return collectingRepository.findByAddressId(addressId)
                    .stream().map(collectingEntity ->
                        UserCollectingDTO.builder()
                                .date(collectingEntity.getDate())
                                .collectingDetailsDTOList(getCollectingDetailsFor(collectingEntity.getId()))
                                .totalPoints(getTotalCollectingPointsFor(collectingEntity.getId()))
                                .totalQuantity(getTotalCollectingQuantityFor(collectingEntity.getId()))
                                .build()).toList();
        } else {
            throw new ResourceNotFoundException("The username: " + username + " does not exist");
        }
    }

    private List<CollectingDetailsDTO> getCollectingDetailsFor(Integer collectingId) {
        return collectingDetailsRepository
                .findByCollectingEntityId(collectingId)
                .stream()
                .map(collectingDetailsEntity ->
                    CollectingDetailsDTO.builder()
                            .points(collectingDetailsEntity.getPoints())
                            .recycledType(collectingDetailsEntity.getRecycledType())
                            .kilograms(collectingDetailsEntity.getKilograms())
                            .build()
                ).toList();
    }

    private Integer getTotalCollectingPointsFor(Integer collectingId) {
        return collectingDetailsRepository
                .findByCollectingEntityId(collectingId)
                .stream()
                .mapToInt(CollectingDetailsEntity::getPoints)
                .sum();
    }

    private Double getTotalCollectingQuantityFor(Integer collectingId) {
        return collectingDetailsRepository
                .findByCollectingEntityId(collectingId)
                .stream()
                .mapToDouble(CollectingDetailsEntity::getKilograms)
                .sum();
    }
}
