package com.devon.building.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends AbstractDTO {
    private String fullName;
    private String phone;
    private String email;
    private String companyName;
    private String demand;
    private String status;
}