package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.CollectingDetailsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollectingDetailsRepository extends JpaRepository<CollectingDetailsEntity, Integer> {
}
