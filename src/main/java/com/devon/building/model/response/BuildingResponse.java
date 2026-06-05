package com.devon.building.model.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildingResponse {
    private Long id;
    private String name;
    private String street;
    private String ward;
    private String district;
    private String structure;
    private Long numberOfBasement;
    private Long floorArea;
    private String direction;
    private String level;
    private Long rentPrice;
    private String rentPriceDescription;
    private String serviceFee;
    private String carFee;
    private String motoFee;
    private String overtimeFee;
    private String waterFee;
    private String electricityFee;
    private String deposit;
    private String payment;
    private String rentTime;
    private String decorationTime;
    private Double brokerageFee;
    private String note;
    private String managerName;
    private String managerPhoneNumber;
    private List<String> typeCodes;
    private List<String> rentArea;
}