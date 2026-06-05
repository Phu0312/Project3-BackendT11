package com.devon.building.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildingUpdateRequest {

    @NotBlank(message = "Name building must not be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Street must not be blank")
    @Size(max = 255)
    private String street;

    @NotBlank(message = "Ward must not be blank")
    @Size(max = 255)
    private String ward;

    @NotBlank(message = "District must not be blank")
    private String district;

    private String structure;
    @PositiveOrZero(message = "Number of basement must be >= 0")
    private Long numberOfBasement;

    @Positive(message = "Floor area must be > 0")
    private Long floorArea;
    private String direction;
    private String level;

    @NotNull(message = "Rent price must not be null")
    @Positive(message = "Rent price must be > 0")
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

    @Pattern(regexp = "^\\d{10}$", message = "Manager phone must be 10 digits")
    private String managerPhoneNumber;

    @NotEmpty(message = "Type codes must not be empty")
    private List<String> typeCodes;

    @NotEmpty(message = "Rent area must not be empty")
    private List<String> rentArea;
}