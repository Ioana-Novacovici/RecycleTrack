package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Integer> {

    List<CollectionEntity> findByAddressId(Integer id);

    Optional<CollectionEntity> findCollectionEntityByDateAndAddressId(LocalDate date, Integer address_id);

//    //TODO use this for monthly classament
//    List<CollectingEntity> findByDate_Month(short dateMonth);
}
