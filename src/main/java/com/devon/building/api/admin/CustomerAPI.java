package com.devon.building.api.admin;

import com.devon.building.model.dto.CustomerDTO;
import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerAPI {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO,
                                                      BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            responseDTO.setMessage("Failed to add customer");
            responseDTO.setDetail(errorMessages);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        customerService.save(customerDTO);

        responseDTO.setMessage("Thêm khách hàng thành công");
        return ResponseEntity.ok(responseDTO);
    }


    @PutMapping
    public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody @Valid CustomerDTO customerDTO,
                                                      BindingResult bindingResult) {
        ResponseDTO responseDTO = new ResponseDTO();

        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();

            responseDTO.setMessage("Failed to update customer");
            responseDTO.setDetail(errorMessages);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDTO);
        }

        customerService.update(customerDTO);

        responseDTO.setMessage("Cập nhật khách hàng thành công");
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping
    public ResponseEntity<ResponseDTO> deleteCustomers(@RequestBody List<Long> ids) {
        customerService.deleteCustomers(ids);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Xóa khách hàng thành công");
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{customerId}/staff")
    public ResponseEntity<?> loadStaffs(@PathVariable Long customerId) {
        return ResponseEntity.ok(customerService.loadStaffs(customerId));
    }

    @PostMapping("/assignment")
    public ResponseEntity<ResponseDTO> assignCustomer(@RequestParam Long customerId,
                                                      @RequestBody List<Long> staffIds) {
        customerService.assignCustomer(customerId, staffIds);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Cập nhật nhân viên thành công");
        return ResponseEntity.ok(responseDTO);
    }
}