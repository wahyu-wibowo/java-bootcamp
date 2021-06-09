package com.mitrais.java.bootcamp.model.persistence;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Account {
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private long id;
	private String name;
	private String pin;
	private String accountNumber;
	private BigDecimal balance;

	public Account() {
		//default constructor
	}

	public Account(long id, String name, String pin, String accountNumber, BigDecimal balance) {
		this.id = id;
		this.name = name;
		this.pin = pin;
		this.accountNumber = accountNumber;
		this.balance = balance;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	@Override
	public String toString() {
		return "Account #" + id + ": [Name = " + name + ", PIN = " + pin + ", Account Number = " + accountNumber + ", Balance = $" + balance.toString() + "]";
	}

}
