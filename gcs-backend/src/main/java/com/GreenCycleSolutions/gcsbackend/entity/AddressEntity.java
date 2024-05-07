package com.GreenCycleSolutions.gcsbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "address", uniqueConstraints = {
        @UniqueConstraint(name = "UniqueAddress", columnNames = {"street", "street_number", "block", "apartment_number"})})
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String street;

    @Column(name = "street_number")
    private Integer streetNumber;

    @Column
    private String block;

    @Column(name = "apartment_number")
    private Integer apartmentNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserEntity user;

    @Column
    @Enumerated(EnumType.STRING)
    private DayOfWeek day;

    @Column(unique = true, name = "collecting_code")
    private UUID collectingCode;

}
