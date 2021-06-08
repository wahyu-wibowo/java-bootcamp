package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
	@Override
	public boolean checkAuth(Account input) throws Exception {
		//validate acc and pin is correct
		return true;
		//throw new Exception("ERROR BOSKU");
	}

	@Override
	public List<Account> getAllAccount() {
		//TODO: get from db later
		List<Account> accounts = new ArrayList<>(Arrays.asList(
				new Account(1L, "John Doe", "012108", "112233", new BigDecimal(100)),
				new Account(2L, "Jane Doe", "932012", "112244", new BigDecimal(30))
		));
		return accounts;
	}
}
