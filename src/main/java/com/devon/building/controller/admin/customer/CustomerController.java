package com.devon.building.controller.admin.customer;

import com.devon.building.constant.SystemConstant;
import com.devon.building.enums.StatusCode;
import com.devon.building.enums.TransactionCode;
import com.devon.building.model.dto.CustomerDTO;
import com.devon.building.model.request.CustomerSearchRequest;
import com.devon.building.service.CustomerService;
import com.devon.building.service.TransactionService;
import com.devon.building.service.UserService;
import com.devon.building.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final UserService userService;
    private final TransactionService transactionService;

    @GetMapping("/admin/customers/list")
    public ModelAndView list(CustomerSearchRequest request) {
        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            request.setStaffId(null);
        }

        ModelAndView mav = new ModelAndView("admin/customer/customerList");
        mav.addObject("customers", customerService.findAll(request));
        mav.addObject("staffs", userService.loadStaff());
        mav.addObject("status", StatusCode.getStatus());
        mav.addObject("params", request);
        return mav;
    }

    @GetMapping({"/admin/customers/edit", "/admin/customers/detail"})
    public ModelAndView edit(Long id) {
        if (id != null && !customerService.hasPermissionToViewCustomer(id)) {
            return new ModelAndView("redirect:/403");
        }

        ModelAndView mav = new ModelAndView("admin/customer/customerEdit");

        CustomerDTO customerDTO = new CustomerDTO();

        if (id != null) {
            customerDTO = customerService.findDTOById(id);
            mav.addObject("CSKH", transactionService.findByCustomerAndCode(id, TransactionCode.CSKH.toString()));
            mav.addObject("DDX", transactionService.findByCustomerAndCode(id, TransactionCode.DDX.toString()));
            mav.addObject("DC", transactionService.findByCustomerAndCode(id, TransactionCode.DC.toString()));
        }

        mav.addObject("customerEdit", customerDTO);
        mav.addObject("status", StatusCode.getStatus());
        mav.addObject("transactionType", TransactionCode.getTransactionCode());

        return mav;
    }
}