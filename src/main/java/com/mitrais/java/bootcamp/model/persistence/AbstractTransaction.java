package com.mitrais.java.bootcamp.model.persistence;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public abstract class AbstractTransaction {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;

	@Column(columnDefinition = "TIMESTAMP")
	@Convert(converter = Jsr310JpaConverters.LocalDateTimeConverter.class)
	private LocalDateTime transactionDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="account")
	private Account account;
	private BigDecimal amount;
	private Boolean isConfirmed;

	public AbstractTransaction() {
		//default constructor
	}

	public AbstractTransaction(LocalDateTime transactionDate, Account account, BigDecimal amount) {
		this.transactionDate = transactionDate;
		this.account = account;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Boolean getConfirmed() {
		return isConfirmed;
	}

	public void setConfirmed(Boolean confirmed) {
		isConfirmed = confirmed;
	}
}
