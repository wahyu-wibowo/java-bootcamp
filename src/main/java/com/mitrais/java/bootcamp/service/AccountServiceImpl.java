package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.persistence.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accRepo;

	@Override
	public List<Account> getAllAccount() {
		return accRepo.findAll();
	}

	@Override
	public void insertCsv(String absolutePath) throws Exception {
		//read file
		List<Account> records = new ArrayList<>();
		List<String> accs = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(absolutePath))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(Constants.COMMA_DELIMITER);

				//bypass first row if value = account_number
				if ("account_number".equals(values[0])) {
					continue;
				}

				records.add(new Account(values[2], values[3], values[0], new BigDecimal(values[1])));
				accs.add(values[0]);
			}
		}

		//validate any duplicate?
		List<Account> available = accRepo.findByAccountNumberIn(accs);
		if (!available.isEmpty()) {
			throw new Exception("Account duplicate: ".concat(available.get(0).getAccountNumber()));
		}

		accRepo.save(records);
	}
}
