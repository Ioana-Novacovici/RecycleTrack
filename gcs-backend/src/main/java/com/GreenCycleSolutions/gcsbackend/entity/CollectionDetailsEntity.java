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
@Table(name = "collection_details")
public class CollectionDetailsEntity {

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
    @JoinColumn(name="collection_id", nullable=false)
    private CollectionEntity collectionEntity;
}
