package com.mitrais.java.bootcamp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Transaction {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private Date transactionDate;

	private String account; //todo: might need to create relation with Account object directly
	private BigDecimal amount;

	// for fund transfer only
	private String destinationAccount;
	private String referenceNumber;

	public Transaction() {
		//default constructor
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
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

	@Override
	public String toString() {
		return "Employee [id=" + id + ", date=" + transactionDate + ", account=" + account + ", amount=" + amount.toString() + ", destinationAccount=" + destinationAccount + ", referenceNumber=" + referenceNumber + "]";
	}

}
