package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.Account;

import java.util.List;

public interface AccountService {
    Account findByAccount(String account) throws Exception;
    void checkAuth(Account input) throws Exception;
    List<Account> getAllAccount();
    void insertCsv(String absolutePath) throws Exception;
    void validateAccount(String accountNumber) throws Exception;
}
