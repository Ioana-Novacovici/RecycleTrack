package com.GreenCycleSolutions.gcsbackend.repository;

import com.GreenCycleSolutions.gcsbackend.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Integer> {

    @Query("SELECT a FROM AddressEntity a WHERE " +
            "(:street IS NULL OR a.street = :street) AND " +
            "(:streetNumber IS NULL OR :streetNumber = 0 OR a.streetNumber = :streetNumber) AND " +
            "(:block IS NULL OR a.block = :block) AND " +
            "(:apartmentNumber IS NULL OR :apartmentNumber = 0 OR a.apartmentNumber = :apartmentNumber) AND" +
            "(:username IS NULL OR a.user.username = :username)")
    List<AddressEntity> findAllBy(@Param("street") String street, @Param("streetNumber") Integer streetNumber, @Param("block") String block,
                                  @Param("apartmentNumber") Integer apartmentNumber, @Param("username")String username);

    List<AddressEntity> findAddressEntitiesByDayEquals(DayOfWeek day);

    Optional<AddressEntity> findByCollectionCode(UUID collectionCode);

    Optional<AddressEntity> findByUserUsername(String username);
}
