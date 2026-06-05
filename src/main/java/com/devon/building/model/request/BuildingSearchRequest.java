package com.devon.building.model.request;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BuildingSearchRequest {
    String name;
    Long floorArea;
    String district;
    String ward;
    String street;
    String numberOfBasement;
    String direction;
    String level;

    Long areaFrom;
    Long areaTo;
    Long rentPriceFrom;
    Long rentPriceTo;

    String managerName;
    String managerPhoneNumber;
    Long staffId;

    List<String> typeCodes;
}
