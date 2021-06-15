package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<AbstractTransaction, Long> {
}
