package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;

import java.util.List;

public interface TransactionService {
    TransactionDto createTransaction(TransactionDto transactionDto) throws Exception;
    AbstractTransaction confirmTransaction(String id) throws Exception;
    List<TransactionDto> inquiryTransaction(TransactionDto transactionDto) throws Exception;
}
