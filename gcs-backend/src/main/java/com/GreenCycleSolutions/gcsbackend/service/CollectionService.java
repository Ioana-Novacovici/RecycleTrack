package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.dto.AgentCollectionDTO;
import com.GreenCycleSolutions.gcsbackend.dto.CollectionDetailsDTO;
import com.GreenCycleSolutions.gcsbackend.dto.UserCollectionDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectionDetailsEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectionEntity;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectionDetailsRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CollectionService {

    private final CollectionDetailsRepository collectionDetailsRepository;
    private final CollectionRepository collectionRepository;
    private final AddressRepository addressRepository;

    public CollectionService(CollectionDetailsRepository collectionDetailsRepository, CollectionRepository collectionRepository, AddressRepository addressRepository) {
        this.collectionDetailsRepository = collectionDetailsRepository;
        this.collectionRepository = collectionRepository;
        this.addressRepository = addressRepository;
    }

    public void addCollection(AgentCollectionDTO collectionDTO) {
        Optional<AddressEntity> addressEntityOptional = addressRepository
                .findByCollectionCode(collectionDTO.getCollectionCode());
        if (addressEntityOptional.isPresent()) {
            var address = addressEntityOptional.get();
            CollectionEntity collection = collectionRepository.save(CollectionEntity.builder()
                    .date(LocalDate.now())
                    .address(address)
                    .build());
            for (Map.Entry<RecycledType, Double> entry : collectionDTO.getQuantities().entrySet()) {
                collectionDetailsRepository.save(CollectionDetailsEntity.builder()
                        .collectionEntity(collection)
                                .recycledType(entry.getKey())
                                .kilograms(entry.getValue())
                                .points(computePoints(entry.getKey(), entry.getValue()))
                        .build());
            }

        } else {
            throw new ResourceNotFoundException("The collection code provided is not correct");
        }
    }

    public List<UserCollectionDTO> getCollections(String username) {
        Optional<AddressEntity> addressEntityOptional = addressRepository.findByUserUsername(username);
        if(addressEntityOptional.isPresent()) {
            var addressId = addressEntityOptional.get().getId();
            return collectionRepository.findByAddressId(addressId)
                    .stream().map(collectionEntity ->
                        UserCollectionDTO.builder()
                                .date(collectionEntity.getDate())
                                .collectionDetailsDTOList(getCollectionDetailsFor(collectionEntity.getId()))
                                .totalPoints(getTotalCollectionPointsFor(collectionEntity.getId()))
                                .totalQuantity(getTotalCollectionQuantityFor(collectionEntity.getId()))
                                .build()).toList();
        } else {
            throw new ResourceNotFoundException("The username: " + username + " does not exist");
        }
    }

    private List<CollectionDetailsDTO> getCollectionDetailsFor(Integer collectionId) {
        return collectionDetailsRepository
                .findByCollectionEntityId(collectionId)
                .stream()
                .map(collectionDetailsEntity ->
                    CollectionDetailsDTO.builder()
                            .points(collectionDetailsEntity.getPoints())
                            .recycledType(collectionDetailsEntity.getRecycledType())
                            .kilograms(collectionDetailsEntity.getKilograms())
                            .build()
                ).toList();
    }

    private Integer getTotalCollectionPointsFor(Integer collectionId) {
        return collectionDetailsRepository
                .findByCollectionEntityId(collectionId)
                .stream()
                .mapToInt(CollectionDetailsEntity::getPoints)
                .sum();
    }

    private Double getTotalCollectionQuantityFor(Integer collectionId) {
        return collectionDetailsRepository
                .findByCollectionEntityId(collectionId)
                .stream()
                .mapToDouble(CollectionDetailsEntity::getKilograms)
                .sum();
    }

    private Integer computePoints(RecycledType type, Double quantity) {
        return switch (type) {
            case GLASS -> (int)(quantity * 5);
            case METAL -> (int)(quantity * 6);
            case PAPER -> (int)(quantity * 7);
            case PLASTIC -> (int)(quantity * 8);
        };
    }
}
