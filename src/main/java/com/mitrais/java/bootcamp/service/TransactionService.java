package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transactionDto) throws Exception;
    AbstractTransaction confirmTransaction(String id) throws Exception;
}
