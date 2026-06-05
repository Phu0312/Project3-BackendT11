package com.devon.building.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "rentarea")
@Getter
@Setter
public class RentArea extends BaseEntity {

    @Column(name = "value")
    private Long value;

    @Column(name = "buildingid")
    private Long buildingId;
}