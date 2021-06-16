package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.persistence.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accRepo;

	@Override
	public Account findByAccount(String account) throws Exception {
		//todo: change to findone
		Optional<Account> result = accRepo.findAll().stream().filter(x -> x.getAccountNumber().equals(account)).findAny();
		if (!result.isPresent()){
			throw new Exception("Invalid Account: Account Not Found");
		} else {
			return result.get();
		}
	}

	@Override
	public void checkAuth(Account input) throws Exception {
		validateAccount(input.getAccountNumber());

		//validate pin length
		if(input.getPin().length() != Constants.PIN_LENGTH) {
			throw new Exception("PIN should have 6 digits length");
		}

		//validate pin only number
		if(!input.getPin().matches("[0-9]+")) {
			throw new Exception("PIN should only contains numbers");
		}

		//validate acc and pin is correct
		//todo: change to findone
		Optional<Account> result = accRepo.findAll().stream().filter(x -> x.getAccountNumber().equals(input.getAccountNumber())).findAny();
		if (!result.isPresent() || !result.get().getPin().equals(input.getPin())){
			throw new Exception("Invalid Account Number/PIN");
		}
	}

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

	@Override
	public void validateAccount(String acc) throws Exception {
		//validate acc only number
		if(!acc.matches("[0-9]+")) {
			throw new Exception("Invalid Account: Account Number should only contains numbers");
		}

		//validate acc length
		if(acc.length() != Constants.ACC_LENGTH) {
			throw new Exception("Account Number should have 6 digits length");
		}
	}
}
