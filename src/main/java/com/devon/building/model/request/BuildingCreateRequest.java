package com.devon.building.model.request;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BuildingCreateRequest {

    @NotBlank(message = "Name building must not be blank")
    @Size(max = 255, message = "Name must not exceed 255 characters")
    private String name;

    @NotBlank(message = "Street must not be blank")
    @Size(max = 255, message = "Street must not exceed 255 characters")
    private String street;

    @NotBlank(message = "Ward must not be blank")
    @Size(max = 255, message = "Ward must not exceed 255 characters")
    private String ward;

    @NotBlank(message = "District must not be blank")
    private String district;

    @Size(max = 255, message = "Structure must not exceed 255 characters")
    private String structure;

    @PositiveOrZero(message = "Number of basement must be >= 0")
    private Long numberOfBasement;

    @Positive(message = "Floor area must be > 0")
    private Long floorArea;

    @Size(max = 255, message = "Direction must not exceed 255 characters")
    private String direction;

    @Size(max = 255, message = "Level must not exceed 255 characters")
    private String level;

    @NotNull(message = "Rent price must not be null")
    @Positive(message = "Rent price must be > 0")
    private Long rentPrice;
    private String rentPriceDescription;

    @Size(max = 255, message = "Service fee must not exceed 255 characters")
    private String serviceFee;

    @Size(max = 255, message = "Car fee must not exceed 255 characters")
    private String carFee;

    @Size(max = 255, message = "Moto fee must not exceed 255 characters")
    private String motoFee;

    @Size(max = 255, message = "Overtime fee must not exceed 255 characters")
    private String overtimeFee;

    @Size(max = 255, message = "Water fee must not exceed 255 characters")
    private String waterFee;

    @Size(max = 255, message = "Electricity fee must not exceed 255 characters")
    private String electricityFee;

    @Size(max = 255, message = "Deposit must not exceed 255 characters")
    private String deposit;

    @Size(max = 255, message = "Payment must not exceed 255 characters")
    private String payment;

    @Size(max = 255, message = "Rent time must not exceed 255 characters")
    private String rentTime;

    @Size(max = 255, message = "Decoration time must not exceed 255 characters")
    private String decorationTime;

    @PositiveOrZero(message = "Brokerage fee must be greater than or equal to 0")
    private Double brokerageFee;

    @Size(max = 255, message = "Note must not exceed 255 characters")
    private String note;

    @Size(max = 255, message = "Manager name must not exceed 255 characters")
    private String managerName;

    @Pattern(regexp = "^\\d{10}$", message = "Manager phone must be 10 digits")
    private String managerPhoneNumber;

    @NotEmpty(message = "Type codes must not be empty")
    private List<String> typeCodes;

    @NotEmpty(message = "Rent area must not be empty")
    private List<String> rentArea;
}