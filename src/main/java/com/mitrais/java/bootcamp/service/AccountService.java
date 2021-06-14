package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.Account;

import java.util.List;

public interface AccountService {
    List<Account> getAllAccount();
    void insertCsv(String absolutePath) throws Exception;
}
