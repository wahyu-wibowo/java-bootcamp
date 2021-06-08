package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Transaction;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

public class TransactionServiceImpl implements TransactionService {
	List<Account> accounts = new ArrayList<>(Arrays.asList(
			new Account(1L, "John Doe", "012108", "112233", new BigDecimal(100)),
			new Account(2L, "Jane Doe", "932012", "112244", new BigDecimal(30)),
			new Account(3L, "Dummy", "321", "123", new BigDecimal(3000))
	));

	List<Transaction> transactions = new ArrayList<>();

	@Override
	public void checkAuth(Account input) throws Exception {
		//validate acc length
		//todo: reactivate later
		/*if(input.getAccountNumber().length() != Constants.ACC_LENGTH) {
			throw new Exception("Account Number should have 6 digits length");
		}*/

		//validate acc only number
		if(!input.getAccountNumber().matches("[0-9]+")) {
			throw new Exception("Account Number should only contains numbers");
		}

		//validate pin length
		//todo: reactivate later
		/*if(input.getPin().length() != Constants.PIN_LENGTH) {
			throw new Exception("PIN should have 6 digits length");
		}*/

		//validate pin only number
		if(!input.getPin().matches("[0-9]+")) {
			throw new Exception("Account Number should only contains numbers");
		}

		//validate acc and pin is correct
		Optional<Account> result = accounts.stream().filter(x -> x.getAccountNumber().equals(input.getAccountNumber())).findAny();
		if (!result.isPresent() || !result.get().getPin().equals(input.getPin())){
			throw new Exception("Invalid Account Number/PIN");
		}
	}

	@Override
	public List<Account> getAllAccount() {
		//TODO: get from db later
		return accounts;
	}

	@Override
	public Account findByAccount(String account) throws Exception {
		//TODO: get from db later
		Optional<Account> result = accounts.stream().filter(x -> x.getAccountNumber().equals(account)).findAny();
		if (!result.isPresent()){
			throw new Exception("Account Not Found");
		} else {
			return result.get();
		}
	}

	@Override
	public Transaction createTransaction(String account, String amount) throws Exception {
		Account acc = findByAccount(account);

		if(!amount.matches("[0-9]+")) {
			throw new Exception("Invalid amount");
		}

		return createTransaction(acc, new BigDecimal(amount));
	}

	@Override
	public Transaction confirmTransaction(String id) throws Exception {
		//saving trx and do journaling
		Optional<Transaction> result = transactions.stream().filter(x -> x.getId() == (Long.valueOf(id)).longValue()).findAny();
		if (!result.isPresent()){
			throw new Exception("Transaction Not Found");
		} else {
			Transaction trx = result.get();
			Account acc = findByAccount(trx.getAccount());

			//revalidate balance is enough
			if (acc.getBalance().compareTo(trx.getAmount()) < 0){
				throw new Exception("Insufficient balance $".concat(trx.getAmount().toString()));
			}

			//journaling
			acc.setBalance(acc.getBalance().subtract(trx.getAmount()));
			trx.setConfirmed(true);

			//todo: save acc n trx to db

			return trx;
		}
	}

	public Transaction createTransaction(Account acc, BigDecimal amt) throws Exception {
		//validate balance is enough
		if (acc.getBalance().compareTo(amt) < 0){
			throw new Exception("Insufficient balance $".concat(amt.toString()));
		}

		//create trx
		Transaction trx = new Transaction(new Date(), acc.getAccountNumber(), amt);

		//check is withdrawal or transfer
		//withdrawal has no destinationAccount
		if (StringUtils.isEmpty(trx.getDestinationAccount())) {
			validateWithdrawal(trx);
		} else {
			validateTransfer(trx);
		}

		//todo: save unconfirmed trx in db
		trx.setId(new Random().nextLong());
		transactions.add(trx);

		return trx;
	}

	public void validateTransaction(Transaction trx) throws Exception{
		//validate max amount per transaction
		if (trx.getAmount().compareTo(Constants.MAX_TRX_AMOUNT) > 0) {
			throw new Exception("Maximum amount to withdraw is $".concat(Constants.MAX_TRX_AMOUNT.toString()));
		}
	}

	public void validateWithdrawal(Transaction trx) throws Exception{
		validateTransaction(trx);

		//validate if withdraw amount is not multiple of $10.
		if(trx.getAmount().remainder(BigDecimal.TEN).compareTo(BigDecimal.ZERO) != 0) {
			throw new Exception("Invalid amount");
		}
	}

	public void validateTransfer(Transaction trx) throws Exception{
		validateTransaction(trx);
	}
}
