package com.devon.building.enums;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public enum StatusCode {
    CHUA_XU_LY("Chưa xử lý"),
    DANG_XU_LY("Đang xử lý"),
    DA_XU_LY("Đã xử lý");

    private final String name;

    StatusCode(String name) {
        this.name = name;
    }

    public static Map<String, String> getStatus() {
        Map<String, String> result = new LinkedHashMap<>();
        for (StatusCode item : StatusCode.values()) {
            result.put(item.toString(), item.getName());
        }
        return result;
    }

    public static String getName(String code) {
        if (code == null) return "";
        for (StatusCode item : StatusCode.values()) {
            if (item.toString().equals(code)) {
                return item.getName();
            }
        }
        return "";
    }
}