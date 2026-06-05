package com.devon.building.api.admin;

import com.devon.building.model.dto.CustomerDTO;
import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerAPI {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createCustomer(@RequestBody @Valid CustomerDTO customerDTO) {
        customerService.save(customerDTO);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Thêm khách hàng thành công");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) {
        customerService.update(customerDTO);

        ResponseDTO responseDTO = new ResponseDTO();
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