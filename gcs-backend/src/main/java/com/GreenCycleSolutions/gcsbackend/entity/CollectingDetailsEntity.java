package com.GreenCycleSolutions.gcsbackend.entity;

import com.GreenCycleSolutions.gcsbackend.entity.enumtype.RecycledType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "collecting_details")
public class CollectingDetailsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private RecycledType recycledType;

    @Column
    private Double kilograms;

    @Column
    private Integer points;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="collecting_id", nullable=false)
    private CollectingEntity collectingEntity;
}
