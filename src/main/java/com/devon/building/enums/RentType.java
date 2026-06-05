package com.devon.building.enums;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter

public enum RentType {
    TANG_TRET("Tầng trệt"),
    NGUYEN_CAN("Nguyên căn"),
    NOI_THAT("Nội thất");

    private final String name;

    RentType(String name) {
        this.name = name;
    }

    public static Map<String, String> getTypeCode() {
        Map<String, String> type = new LinkedHashMap<>();
        for (RentType d : RentType.values()) {
            type.put(d.toString(), d.name);
        }
        return type;
    }
}
