package com.devon.building.service;

import com.devon.building.entity.Customer;
import com.devon.building.model.dto.CustomerDTO;
import com.devon.building.model.request.CustomerSearchRequest;
import com.devon.building.model.response.CustomerResponse;
import com.devon.building.model.response.StaffResponse;

import java.util.List;

public interface CustomerService {
    List<CustomerResponse> findAll(CustomerSearchRequest request);

    Customer findById(Long id);

    CustomerDTO findDTOById(Long id);

    void save(CustomerDTO customerDTO);

    void update(CustomerDTO customerDTO);

    void deleteCustomers(List<Long> ids);

    List<StaffResponse> loadStaffs(Long customerId);

    void assignCustomer(Long customerId, List<Long> staffIds);

    boolean hasPermissionToViewCustomer(Long customerId);
}