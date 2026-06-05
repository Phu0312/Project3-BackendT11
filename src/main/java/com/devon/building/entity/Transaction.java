package com.devon.building.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "transaction")
@Getter
@Setter
public class Transaction extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "customerid")
    private Customer customer;

    @Column(name = "code")
    private String code;

    @Column(name = "note")
    private String note;

    @Column(name = "staffid")
    private Long staffId;

    @Column(name = "is_active")
    private Boolean active = true;
}