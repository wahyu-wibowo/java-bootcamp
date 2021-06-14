package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<AbstractTransaction, Long> {
    List<AbstractTransaction> findByTransactionDateBetweenAndIsConfirmedTrueOrderByTransactionDateDesc(LocalDateTime before, LocalDateTime after);
}
