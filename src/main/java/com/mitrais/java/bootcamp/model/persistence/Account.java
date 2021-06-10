package com.mitrais.java.bootcamp.model.persistence;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Account {
	@Id
	private String accountNumber;
	private String name;
	private String pin;
	private BigDecimal balance;

	public Account() {
		//default constructor
	}

	public Account(String name, String pin, String accountNumber, BigDecimal balance) {
		this.name = name;
		this.pin = pin;
		this.accountNumber = accountNumber;
		this.balance = balance;
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
		return "Account # " + ": [Name = " + name + ", PIN = " + pin + ", Account Number = " + accountNumber + ", Balance = $" + balance.toString() + "]";
	}

}
