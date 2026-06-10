package com.devon.building.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO extends AbstractDTO {
    @NotBlank(message = "Họ và tên không được để trống")
    private String fullName;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;
    private String email;
    private String companyName;
    private String demand;

    @NotBlank(message = "Trạng thái không được để trống")
    private String status;
}