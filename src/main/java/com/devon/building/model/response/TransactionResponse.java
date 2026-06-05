package com.devon.building.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    private Long id;
    private String code;
    private String note;
    private String createdBy;
    private String createdDate;
    private String modifiedBy;
    private String modifiedDate;
}