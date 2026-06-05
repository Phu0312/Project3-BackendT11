package com.devon.building.repository.custom;

import com.devon.building.builder.CustomerSearchBuilder;
import com.devon.building.entity.Customer;

import java.util.List;

public interface CustomerRepositoryCustom {
    List<Customer> search(CustomerSearchBuilder builder);
}