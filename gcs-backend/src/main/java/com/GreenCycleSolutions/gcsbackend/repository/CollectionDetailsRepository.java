package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.CollectionDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CollectionDetailsRepository extends JpaRepository<CollectionDetailsEntity, Integer> {

    List<CollectionDetailsEntity> findByCollectionEntityId(Integer id);
}
