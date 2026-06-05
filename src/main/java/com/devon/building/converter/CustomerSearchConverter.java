package com.devon.building.converter;

import com.devon.building.builder.CustomerSearchBuilder;
import com.devon.building.entity.Customer;
import com.devon.building.enums.StatusCode;
import com.devon.building.model.request.CustomerSearchRequest;
import com.devon.building.model.response.CustomerResponse;
import org.springframework.stereotype.Component;

@Component
public class CustomerSearchConverter {

    public CustomerSearchBuilder convertToBuilder(CustomerSearchRequest request) {
        return new CustomerSearchBuilder.Builder()
                .setFullName(request.getFullName())
                .setPhone(request.getPhone())
                .setEmail(request.getEmail())
                .setStaffId(request.getStaffId())
                .setStatus(request.getStatus())
                .build();
    }

    public CustomerResponse toResponse(Customer customer) {
        CustomerResponse response = new CustomerResponse();
        response.setId(customer.getId());
        response.setFullName(customer.getFullName());
        response.setPhone(customer.getPhone());
        response.setEmail(customer.getEmail());
        response.setCompanyName(customer.getCompanyName());
        response.setDemand(customer.getDemand());
        response.setStatus(customer.getStatus());
        response.setStatusName(StatusCode.getName(customer.getStatus()));
        response.setCreatedBy(customer.getCreatedBy());
        response.setCreatedDate(customer.getCreatedDate() == null ? "" : customer.getCreatedDate().toString());
        return response;
    }
}