package com.devon.building.service.impl;

import com.devon.building.constant.SystemConstant;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public List<CustomerResponse> findAll(CustomerSearchRequest request) {
        List<Customer> customers = customerRepository.findAll(request);
        List<CustomerResponse> result = new ArrayList<>();

        for (Customer customer : customers) {
            CustomerResponse dto = new CustomerResponse();
            dto.setId(customer.getId());
            dto.setFullName(customer.getFullName());
            dto.setPhone(customer.getPhone());
            dto.setEmail(customer.getEmail());
            dto.setCompanyName(customer.getCompanyName());
            dto.setDemand(customer.getDemand());
            dto.setStatus(customer.getStatus());
            dto.setStatusName(StatusCode.getName(customer.getStatus()));
            dto.setCreatedBy(customer.getCreatedBy());
            dto.setCreatedDate(customer.getCreatedDate() == null ? "" : customer.getCreatedDate().toString());
            result.add(dto);
        }

        return result;
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));
    }

    @Override
    public CustomerDTO findDTOById(Long id) {
        Customer customer = findById(id);

        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setCompanyName(customer.getCompanyName());
        dto.setDemand(customer.getDemand());
        dto.setStatus(customer.getStatus());

        return dto;
    }

    @Override
    public void save(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setFullName(customerDTO.getFullName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setCompanyName(customerDTO.getCompanyName());
        customer.setDemand(customerDTO.getDemand());
        customer.setStatus(customerDTO.getStatus() == null || customerDTO.getStatus().isBlank()
                ? StatusCode.CHUA_XU_LY.toString()
                : customerDTO.getStatus());
        customer.setActive(true);

        customerRepository.save(customer);
    }

    @Override
    public void update(CustomerDTO customerDTO) {
        Customer customer = findById(customerDTO.getId());

        customer.setFullName(customerDTO.getFullName());
        customer.setPhone(customerDTO.getPhone());
        customer.setEmail(customerDTO.getEmail());
        customer.setCompanyName(customerDTO.getCompanyName());
        customer.setDemand(customerDTO.getDemand());
        customer.setStatus(customerDTO.getStatus());

        customerRepository.save(customer);
    }

    @Override
    public void deleteCustomers(List<Long> ids) {
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

        List<StaffResponse> result = new ArrayList<>();

        for (User staff : staffs) {
            StaffResponse dto = new StaffResponse();
            dto.setId(staff.getId());
            dto.setUserName(staff.getUserName());

            boolean checked = customer.getUsers()
                    .stream()
                    .anyMatch(user -> user.getId().equals(staff.getId()));

            dto.setChecked(checked ? "checked" : "");
            result.add(dto);
        }

        return result;
    }

    @Override
    public void assignCustomer(Long customerId, List<Long> staffIds) {
        Customer customer = findById(customerId);
        List<User> staffs = userRepository.findAllById(staffIds);
        customer.setUsers(staffs);
        customerRepository.save(customer);
    }
}