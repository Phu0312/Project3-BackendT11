package com.devon.building.service.impl;

import com.devon.building.entity.Customer;
import com.devon.building.entity.Transaction;
import com.devon.building.model.dto.TransactionDTO;
import com.devon.building.model.response.TransactionResponse;
import com.devon.building.repository.CustomerRepository;
import com.devon.building.repository.TransactionRepository;
import com.devon.building.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final CustomerRepository customerRepository;

    @Override
    public List<TransactionResponse> findByCustomerAndCode(Long customerId, String code) {
        List<Transaction> transactions =
                transactionRepository.findByCustomer_IdAndCodeAndActiveTrueOrderByCreatedDateDesc(customerId, code);

        List<TransactionResponse> result = new ArrayList<>();

        for (Transaction transaction : transactions) {
            TransactionResponse dto = new TransactionResponse();
            dto.setId(transaction.getId());
            dto.setCode(transaction.getCode());
            dto.setNote(transaction.getNote());
            dto.setCreatedBy(transaction.getCreatedBy());
            dto.setCreatedDate(transaction.getCreatedDate() == null ? "" : transaction.getCreatedDate().toString());
            dto.setModifiedBy(transaction.getModifiedBy());
            dto.setModifiedDate(transaction.getModifiedDate() == null ? "" : transaction.getModifiedDate().toString());
            result.add(dto);
        }

        return result;
    }

    @Override
    public void save(TransactionDTO transactionDTO) {
        Customer customer = customerRepository.findById(transactionDTO.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khách hàng"));

        Transaction transaction = new Transaction();
        transaction.setCustomer(customer);
        transaction.setCode(transactionDTO.getCode());
        transaction.setNote(transactionDTO.getNote());
        transaction.setStaffId(transactionDTO.getStaffId());
        transaction.setActive(true);

        transactionRepository.save(transaction);
    }

    @Override
    public void update(TransactionDTO transactionDTO) {
        Transaction transaction = transactionRepository.findById(transactionDTO.getId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

        transaction.setCode(transactionDTO.getCode());
        transaction.setNote(transactionDTO.getNote());
        transaction.setStaffId(transactionDTO.getStaffId());

        transactionRepository.save(transaction);
    }

    @Override
    public void delete(Long id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy giao dịch"));

        transaction.setActive(false);
        transactionRepository.save(transaction);
    }
}