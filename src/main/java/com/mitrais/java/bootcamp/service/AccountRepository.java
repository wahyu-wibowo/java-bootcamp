package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, String> {
    List<Account> findByAccountNumberIn(List<String> accs);
}
