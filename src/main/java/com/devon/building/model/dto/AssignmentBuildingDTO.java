package com.devon.building.model.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentBuildingDTO {
    Long buildingId;
    List<Long> staffId;
}