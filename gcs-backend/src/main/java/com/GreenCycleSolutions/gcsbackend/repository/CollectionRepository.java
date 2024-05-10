package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Integer> {

    List<CollectionEntity> findByAddressId(Integer id);

//    //TODO use this for monthly classament
//    List<CollectingEntity> findByDate_Month(short dateMonth);
}
