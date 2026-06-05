package com.devon.building.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Response {
    Object data;
    String error;
    List<String> details;
}
