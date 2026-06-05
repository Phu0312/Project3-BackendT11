package com.devon.building.service;

import com.devon.building.model.dto.TransactionDTO;
import com.devon.building.model.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    List<TransactionResponse> findByCustomerAndCode(Long customerId, String code);

    void save(TransactionDTO transactionDTO);

    void update(TransactionDTO transactionDTO);

    void delete(Long id);
}