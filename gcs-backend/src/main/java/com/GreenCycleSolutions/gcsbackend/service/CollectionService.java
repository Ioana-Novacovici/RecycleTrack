package com.GreenCycleSolutions.gcsbackend.service;

import com.GreenCycleSolutions.gcsbackend.converter.CollectionConverter;
import com.GreenCycleSolutions.gcsbackend.dto.AgentCollectionDTO;
import com.GreenCycleSolutions.gcsbackend.dto.CollectionDetailsDTO;
import com.GreenCycleSolutions.gcsbackend.dto.UserCollectionDTO;
import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import com.GreenCycleSolutions.gcsbackend.entity.CollectionEntity;
import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import com.GreenCycleSolutions.gcsbackend.exception.ResourceNotFoundException;
import com.GreenCycleSolutions.gcsbackend.repository.AddressRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectionDetailsRepository;
import com.GreenCycleSolutions.gcsbackend.repository.CollectionRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.GreenCycleSolutions.gcsbackend.converter.CollectionConverter.*;

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
            CollectionEntity collection = collectionRepository.save(convertToCollectionEntity(address));
            Integer totalPoints = 0;
            Double totalQuantity = 0.0;
            for (Map.Entry<RecycledType, Double> entry : collectionDTO.getQuantities().entrySet()) {
                collectionDetailsRepository.save(convertToCollectionDetailsEntity(entry, collection));
                totalPoints += computePoints(entry.getKey(), entry.getValue());
                totalQuantity += entry.getValue();
            }
            collection.setTotalPoints(totalPoints);
            collection.setTotalQuantity(totalQuantity);
            collectionRepository.save(collection);

        } else {
            throw new ResourceNotFoundException("The collection code provided is not correct");
        }
    }

    public void useCollectionPoints(UserCollectionDTO collectionDTO) {
        Optional<AddressEntity> addressEntityOptional = addressRepository.findByUserUsername(collectionDTO.getUsername());
        if (addressEntityOptional.isPresent()) {
            var addressId = addressEntityOptional.get().getId();
            Optional<CollectionEntity> collectionEntityOptional = collectionRepository
                    .findCollectionEntityByDateAndAddressId(collectionDTO.getDate(), addressId);
            if(collectionEntityOptional.isPresent()) {
                var collection = collectionEntityOptional.get();
                collection.setIsUsed(true);
                collectionRepository.save(collection);
            } else {
                throw new ResourceNotFoundException("The collection date provided is not correct");
            }
        } else {
            throw new ResourceNotFoundException("The username: " + collectionDTO.getUsername() + " does not exist");
        }
    }

    public List<UserCollectionDTO> getCollections(String username) {
        Optional<AddressEntity> addressEntityOptional = addressRepository.findByUserUsername(username);
        if (addressEntityOptional.isPresent()) {
            var addressId = addressEntityOptional.get().getId();
            var collections = collectionRepository.findByAddressId(addressId);
            if (collections.isEmpty())
                throw new ResourceNotFoundException("The username: " + username + " does not have any collections");
            return collections.stream().map(collectionEntity ->
                    convertToCollectionDTO(collectionEntity, getCollectionDetailsFor(collectionEntity.getId()))).toList();
        } else {
            throw new ResourceNotFoundException("The username: " + username + " does not exist");
        }
    }

    public List<UserCollectionDTO> getWeeklyTopCollections() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<CollectionEntity> collectionEntities = collectionRepository
                .findCollectionEntityByDateBetweenOrderByTotalQuantityDesc(firstDayOfWeek, lastDayOfWeek);
        if(collectionEntities.isEmpty()) {
            throw new ResourceNotFoundException("No collections this week.");
        }
        return collectionEntities.stream().map(
                (collectionEntity) -> {
                    var id = collectionEntity.getAddress().getId();
                    var address = addressRepository.findById(id);
                    if(address.isPresent()){
                        var username = address.get().getUser().getUsername();
                        return convertToCollectionDTO(collectionEntity, username);
                    } else {
                        throw new ResourceNotFoundException("Address not found");
                    }
                }
        ).limit(3).toList();
    }

    private List<CollectionDetailsDTO> getCollectionDetailsFor(Integer collectionId) {
        return collectionDetailsRepository
                .findByCollectionEntityId(collectionId)
                .stream()
                .map(CollectionConverter::convertToCollectionDetailsDTO)
                .toList();
    }
}
