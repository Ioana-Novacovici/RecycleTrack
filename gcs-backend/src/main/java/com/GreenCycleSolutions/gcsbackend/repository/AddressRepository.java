package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

}
