package com.mitrais.java.bootcamp.model.dto;

public class TransactionDto {
	private Long id;
	private String account;
	private String amount;
	private String destinationAccount;
	private String referenceNumber;
	private String date;
	private String balance;

	public TransactionDto() {
		//default
	}

	public TransactionDto(String account, String amount) {
		this.account = account;
		this.amount = amount;
	}

	public TransactionDto(String account, String amount, String destinationAccount) {
		this.account = account;
		this.amount = amount;
		this.destinationAccount = destinationAccount;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
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

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
}
