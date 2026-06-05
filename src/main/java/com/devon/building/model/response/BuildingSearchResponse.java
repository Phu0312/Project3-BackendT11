package com.devon.building.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuildingSearchResponse {
    private Long id;
    private String name;
    private String address;
    private String numberOfBasement;
    private String managerName;
    private String managerPhoneNumber;
    private Long floorArea;
    private String rentArea;
    private Long emptyArea;
    private Long rentPrice;
    private String serviceFee;
    private Double brokerageFee;
}
