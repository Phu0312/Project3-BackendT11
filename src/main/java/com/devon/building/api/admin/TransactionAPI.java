package com.devon.building.api.admin;

import com.devon.building.model.dto.ResponseDTO;
import com.devon.building.model.dto.TransactionDTO;
import com.devon.building.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionAPI {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<ResponseDTO> createTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.save(transactionDTO);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Thêm giao dịch thành công");
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateTransaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.update(transactionDTO);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Cập nhật giao dịch thành công");
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteTransaction(@PathVariable Long id) {
        transactionService.delete(id);

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setMessage("Xóa giao dịch thành công");
        return ResponseEntity.ok(responseDTO);
    }
}