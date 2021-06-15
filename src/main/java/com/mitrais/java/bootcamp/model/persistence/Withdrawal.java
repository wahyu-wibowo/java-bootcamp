package com.mitrais.java.bootcamp.model.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Withdrawal extends AbstractTransaction {
	public Withdrawal() {
		//default
	}

	public Withdrawal(LocalDateTime transactionDate, Account account, BigDecimal amount) {
		super(transactionDate, account, amount);
	}
}
