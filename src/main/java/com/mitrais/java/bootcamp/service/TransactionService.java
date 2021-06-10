package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Transaction;

import java.util.List;

public interface TransactionService {
    void checkAuth(Account input) throws Exception;
    List<Account> getAllAccount();
    Account findByAccount(String account) throws Exception;
    TransactionDto createTransaction(String account, String amount) throws Exception;
    TransactionDto createTransaction(String srcAcc, String dstAcc, String amount) throws Exception;
    Transaction confirmTransaction(String id) throws Exception;
}
