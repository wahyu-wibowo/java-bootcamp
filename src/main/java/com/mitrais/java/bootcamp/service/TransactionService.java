package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.Account;

import java.util.List;

public interface TransactionService {
    boolean checkAuth(Account input) throws Exception;
    List<Account> getAllAccount();
}
