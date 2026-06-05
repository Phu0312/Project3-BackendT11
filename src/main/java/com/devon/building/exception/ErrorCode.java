package com.devon.building.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ErrorCode {

    BUILDING_NOT_FOUND(1000, "Building not found"),
    BUILDING_ID_REQUIRED(1001, "Building is required");
    int code;
    String message;
}
