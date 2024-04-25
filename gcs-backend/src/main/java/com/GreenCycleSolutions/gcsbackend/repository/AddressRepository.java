package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

    @Query("SELECT a FROM AddressEntity a WHERE " +
            "(:street IS NULL OR a.street = :street) AND " +
            "(:streetNumber IS NULL OR :streetNumber = 0 OR a.streetNumber = :streetNumber) AND " +
            "(:block IS NULL OR a.block = :block) AND " +
            "(:entrance IS NULL OR a.entrance = :entrance) AND " +
            "(:apartmentNumber IS NULL OR :apartmentNumber = 0 OR a.apartmentNumber = :apartmentNumber) AND" +
            "(:username IS NULL OR a.user.username = :username)")
    List<AddressEntity> findAllBy(@Param("street") String street, @Param("streetNumber") Integer streetNumber, @Param("block") String block,
                                  @Param("entrance") String entrance, @Param("apartmentNumber") Integer apartmentNumber, @Param("username")String username);
}
