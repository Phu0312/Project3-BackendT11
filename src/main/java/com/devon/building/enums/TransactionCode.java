package com.devon.building.enums;

import lombok.Getter;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public enum TransactionCode {
    CSKH("Chăm sóc khách hàng"),
    DDX("Dẫn đi xem"),
    DC("Đặt cọc");

    private final String name;

    TransactionCode(String name) {
        this.name = name;
    }

    public static Map<String, String> getTransactionCode() {
        Map<String, String> result = new LinkedHashMap<>();
        for (TransactionCode item : TransactionCode.values()) {
            result.put(item.toString(), item.getName());
        }
        return result;
    }
}