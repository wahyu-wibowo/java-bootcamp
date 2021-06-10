package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

}
