package com.devon.building.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO extends AbstractDTO {
    private String code;
    private String note;
    private Long customerId;
    private Long staffId;
}