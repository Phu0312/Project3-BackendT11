package com.devon.building.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BuildingDTO extends AbstractDTO {
    @NotBlank(message = "Name Building not be Blank")
    private String name;

    @NotBlank(message = "Street not be Blank")
    private String street;

    @NotBlank(message = "Ward not be Blank")
    private String ward;

    @NotBlank(message = "District not be Blank")
    private String district;

    private Long numberOfBasement;
    private Long floorArea;
    private String level;

    @Size(min = 1, message = "Type Code is required")
    private List<String> typeCodes;

    private String overtimeFee;
    private String electricityFee;
    private String deposit;
    private String payment;
    private String rentTime;
    private String decorationTime;
    private String rentPriceDescription;
    private String carFee;
    private String motoFee;
    private String waterFee;
    private String structure;
    private String direction;
    private String note;

    @NotEmpty(message = "Rent area not be Blank")
    private List<String> rentArea;

    private String managerName;

    @Size(min = 10, message = "Manager Phone must not be less than 10 digit")
    private String managerPhoneNumber;

    private Long rentPrice;
    private String serviceFee;
    private double brokerageFee;
}
