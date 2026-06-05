package com.devon.building.repository.custom;

import com.devon.building.entity.Customer;
import com.devon.building.model.request.CustomerSearchRequest;

import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> findAll(CustomerSearchRequest request);
}