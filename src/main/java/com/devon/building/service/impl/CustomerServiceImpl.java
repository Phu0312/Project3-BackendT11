package com.devon.building.service.impl;

import com.devon.building.builder.CustomerSearchBuilder;
import com.devon.building.constant.SystemConstant;
import com.devon.building.converter.CustomerMapper;
import com.devon.building.converter.CustomerSearchConverter;
import com.devon.building.entity.Customer;
import com.devon.building.entity.User;
import com.devon.building.enums.StatusCode;
import com.devon.building.model.dto.CustomerDTO;
import com.devon.building.model.request.CustomerSearchRequest;
import com.devon.building.model.response.CustomerResponse;
import com.devon.building.model.response.StaffResponse;
import com.devon.building.repository.CustomerRepository;
import com.devon.building.repository.UserRepository;
import com.devon.building.service.CustomerService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    UserRepository userRepository;
    CustomerSearchConverter customerSearchConverter;
    CustomerMapper customerMapper;

    @Override
    public List<CustomerResponse> findAll(CustomerSearchRequest request) {
        CustomerSearchBuilder builder = customerSearchConverter.convertToBuilder(request);
        List<Customer> customers = customerRepository.search(builder);

        return customers.stream()
                .map(customerSearchConverter::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
    }

    @Override
    public CustomerDTO findDTOById(Long id) {
        Customer customer = findById(id);
        return customerMapper.toDTO(customer);
    }

    @Override
    public void save(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);

        if (customer.getStatus() == null || customer.getStatus().isBlank()) {
            customer.setStatus(StatusCode.CHUA_XU_LY.toString());
        }

        customer.setActive(true);
        customerRepository.save(customer);
    }

    @Override
    public void update(CustomerDTO customerDTO) {
        Customer customer = findById(customerDTO.getId());
        customerMapper.updateEntity(customer, customerDTO);
        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomers(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }

        for (Long id : ids) {
            Customer customer = findById(id);
            customer.setActive(false);
            customerRepository.save(customer);
        }
    }

    @Override
    public List<StaffResponse> loadStaffs(Long customerId) {
        Customer customer = findById(customerId);
        List<User> staffs = userRepository.findByUserRoleAndActiveTrue(SystemConstant.STAFF_ROLE);

        List<StaffResponse> responses = new ArrayList<>();

        for (User staff : staffs) {
            StaffResponse item = new StaffResponse();
            item.setId(staff.getId());
            item.setUserName(staff.getUserName());

            boolean checked = customer.getUsers()
                    .stream()
                    .anyMatch(user -> user.getId().equals(staff.getId()));

            item.setChecked(checked ? "checked" : "");
            responses.add(item);
        }

        return responses;
    }

    @Override
    public void assignCustomer(Long customerId, List<Long> staffIds) {
        Customer customer = findById(customerId);

        if (staffIds == null || staffIds.isEmpty()) {
            customer.setUsers(new ArrayList<>());
        } else {
            List<User> staffs = userRepository.findAllById(staffIds);
            customer.setUsers(staffs);
        }

        customerRepository.save(customer);
    }
}