package com.mitrais.java.bootcamp.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionInquiryData {
	private LocalDateTime transactionDate;
	private String account;
	private BigDecimal amount;
	private String destinationAccount;
	private String referenceNumber;

	public TransactionInquiryData(String account, BigDecimal amount, String destinationAccount, String referenceNumber, LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
		this.account = account;
		this.amount = amount;
		this.destinationAccount = destinationAccount;
		this.referenceNumber = referenceNumber;
	}

	public LocalDateTime getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDateTime transactionDate) {
		this.transactionDate = transactionDate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDestinationAccount() {
		return destinationAccount;
	}

	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	public String getReferenceNumber() {
		return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
}
