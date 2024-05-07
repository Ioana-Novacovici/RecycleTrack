package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.CollectingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectingRepository extends JpaRepository<CollectingEntity, Integer> {
}
