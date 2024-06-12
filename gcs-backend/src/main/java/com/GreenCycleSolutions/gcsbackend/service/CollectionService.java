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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.GreenCycleSolutions.gcsbackend.converter.CollectionConverter.*;

@Service
@RequiredArgsConstructor
public class CollectionService {

    private final CollectionDetailsRepository collectionDetailsRepository;
    private final CollectionRepository collectionRepository;
    private final AddressRepository addressRepository;

    public void addCollection(AgentCollectionDTO collectionDTO) {
        var address = addressRepository
                .findByCollectionCode(collectionDTO.getCollectionCode())
                .orElseThrow(() -> new ResourceNotFoundException("The collection code provided is not correct"));

        CollectionEntity collection = collectionRepository.save(convertToCollectionEntity(address));
        Integer totalPoints = 0;
        Double totalQuantity = 0.0;
        for (Map.Entry<RecycledType, Double> entry : collectionDTO.getQuantities().entrySet()) {
            collectionDetailsRepository.save(convertToCollectionDetailsEntity(entry, collection));
            totalPoints += computePoints(entry.getKey(), entry.getValue());
            totalQuantity += entry.getValue();
        }
        collection.setTotalPoints(totalPoints);
        var roundedTo2Decimals = new BigDecimal(totalQuantity).setScale(2, RoundingMode.HALF_UP).doubleValue();
        collection.setTotalQuantity(roundedTo2Decimals);
        collectionRepository.save(collection);

    }

    public void useCollectionPoints(UserCollectionDTO collectionDTO) {
        var address = addressRepository.findByUserUsername(collectionDTO.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("The username: " + collectionDTO.getUsername() + " does not exist"));
        var addressId = address.getId();
        var collection = collectionRepository
                .findCollectionEntityByDateAndAddressId(collectionDTO.getDate(), addressId)
                .orElseThrow(() -> new ResourceNotFoundException("The collection date provided is not correct"));
        collection.setIsUsed(true);
        collectionRepository.save(collection);
    }

    public List<UserCollectionDTO> getCollections(String username) {
        var address = addressRepository.findByUserUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("The username: " + username + " does not exist"));
        var addressId = address.getId();
        var collections = collectionRepository.findByAddressId(addressId);
        if (collections.isEmpty())
            throw new ResourceNotFoundException("The username: " + username + " does not have any collections");
        return collections.stream().map(collectionEntity ->
                convertToCollectionDTO(collectionEntity, getCollectionDetailsFor(collectionEntity.getId()))).toList();
    }

    public List<UserCollectionDTO> getWeeklyTopCollections() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfWeek = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate lastDayOfWeek = today.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        List<CollectionEntity> collectionEntities = collectionRepository
                .findCollectionEntityByDateBetweenOrderByTotalQuantityDesc(firstDayOfWeek, lastDayOfWeek);
        if (collectionEntities.isEmpty()) {
            throw new ResourceNotFoundException("No collections this week.");
        }
        return collectionEntities.stream().map(
                (collectionEntity) -> {
                    var id = collectionEntity.getAddress().getId();
                    var address = addressRepository.findById(id);
                    if (address.isPresent()) {
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
