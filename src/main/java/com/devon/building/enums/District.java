package com.devon.building.enums;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter

public enum District {
    QUAN_1("Quận 1"),
    QUAN_2("Quận 2"),
    QUAN_3("Quận 3"),
    QUAN_4("Quận 4");

    private final String districtName;

    District(String districtName) {
        this.districtName = districtName;
    }

    public static Map<String, String> getDistrict() {
        Map<String, String> district = new LinkedHashMap<>();
        for (District d : District.values()) {
            district.put(d.toString(), d.districtName);
        }
        return district;
    }
}
