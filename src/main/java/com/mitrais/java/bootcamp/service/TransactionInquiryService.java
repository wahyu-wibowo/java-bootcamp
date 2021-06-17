package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;

import java.util.List;

public interface TransactionInquiryService {
    List<TransactionDto> inquiryTransaction(TransactionDto transactionDto) throws Exception;
}
