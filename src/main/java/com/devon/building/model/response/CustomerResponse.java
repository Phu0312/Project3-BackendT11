package com.devon.building.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerResponse {
    private Long id;
    private String fullName;
    private String phone;
    private String email;
    private String companyName;
    private String demand;
    private String status;
    private String statusName;
    private String createdBy;
    private String createdDate;
}