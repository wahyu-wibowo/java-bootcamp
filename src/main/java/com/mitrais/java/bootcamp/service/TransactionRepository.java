package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByTransactionDateBetweenAndIsConfirmedTrueOrderByTransactionDate(LocalDateTime before, LocalDateTime after);
}
