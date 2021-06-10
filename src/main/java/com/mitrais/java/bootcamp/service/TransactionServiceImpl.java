package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private AccountRepository accRepo;

	@Autowired
	private TransactionRepository trxRepo;

	List<Account> accounts = new ArrayList<>(Arrays.asList(
			new Account("John Doe", "012108", "112233", new BigDecimal(100)),
			new Account("Jane Doe", "932012", "112244", new BigDecimal(30)),
			new Account("Dummy 1", "321", "123", new BigDecimal(3000)),
			new Account( "Dummy 2", "321", "213", new BigDecimal(0))
	));

	@PostConstruct
	public void injectData() {
		//todo: move this later to import.sql
		accRepo.save(accounts);
	}

	@Override
	public void checkAuth(Account input) throws Exception {
		validateAccount(input.getAccountNumber());

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
	public Account findByAccount(String account) throws Exception {
		Optional<Account> result = accRepo.findAll().stream().filter(x -> x.getAccountNumber().equals(account)).findAny();
		if (!result.isPresent()){
			throw new Exception("Account Not Found");
		} else {
			return result.get();
		}
	}

	@Override
	public TransactionDto createTransaction(String account, String amount) throws Exception {
		return createTransaction(account, null, amount);
	}

	@Override
	public TransactionDto createTransaction(String srcAcc, String dstAcc, String amount) throws Exception {
		Account src = findByAccount(srcAcc);
		Account dst = null;
		
		if (!StringUtils.isEmpty(dstAcc)) {
			validateAccount(dstAcc);
			dst = findByAccount(dstAcc);
		}

		if(!amount.matches("[0-9]+([,.][0-9]{1,2})?")) {
			throw new Exception("Invalid amount");
		}

		return createTransaction(src, dst, new BigDecimal(amount));
	}

	@Override
	public Transaction confirmTransaction(String id) throws Exception {
		//saving trx and do journaling
		//todo: might need to check trx confirmation status
		Transaction trx = trxRepo.findOne(Long.valueOf(id));
		if (trx == null || trx.getAmount() == null){
			throw new Exception("Transaction Not Found");
		} else {
			Account acc = findByAccount(trx.getAccount());

			//revalidate balance is enough
			if (acc.getBalance().compareTo(trx.getAmount()) < 0){
				throw new Exception("Insufficient balance $".concat(trx.getAmount().toString()));
			}

			//journaling
			if (!StringUtils.isEmpty(trx.getDestinationAccount())) {
				//transfer journal
				Account destAcc = findByAccount(trx.getDestinationAccount());
				destAcc.setBalance(destAcc.getBalance().add(trx.getAmount()));
				accRepo.save(destAcc);
			}
			acc.setBalance(acc.getBalance().subtract(trx.getAmount()));
			trx.setConfirmed(true);

			//save acc n trx to db
			accRepo.save(acc);
			trxRepo.save(trx);

			return trx;
		}
	}

	private TransactionDto createTransaction(Account srcAcc, Account dstAcc, BigDecimal amt) throws Exception {
		//validate balance is enough
		if (srcAcc.getBalance().compareTo(amt) < 0){
			throw new Exception("Insufficient balance $".concat(amt.toString()));
		}

		//create trx
		Transaction trx = new Transaction(LocalDateTime.now(), srcAcc.getAccountNumber(), amt);

		//check is withdrawal or transfer
		//withdrawal has no dstAcc
		if (dstAcc == null) {
			validateWithdrawal(trx);
		} else {
			trx.setDestinationAccount(dstAcc.getAccountNumber());
			trx.setReferenceNumber(generateRefNo());
			validateTransfer(trx);
		}

		//save unconfirmed trx in db
		trxRepo.save(trx);

		return convertToDto(trx, srcAcc);
	}

	private void validateTransaction(Transaction trx) throws Exception{
		//validate max amount per transaction
		if (trx.getAmount().compareTo(Constants.MAX_TRX_AMOUNT) > 0) {
			throw new Exception("Maximum amount to withdraw is $".concat(Constants.MAX_TRX_AMOUNT.toString()));
		}
	}

	private void validateWithdrawal(Transaction trx) throws Exception{
		validateTransaction(trx);

		//validate if withdraw amount is not multiple of $10.
		if(trx.getAmount().remainder(BigDecimal.TEN).compareTo(BigDecimal.ZERO) != 0) {
			throw new Exception("Invalid amount");
		}
	}

	private void validateTransfer(Transaction trx) throws Exception{
		validateTransaction(trx);

		//validate if transfer amount is < 1
		if (trx.getAmount().compareTo(Constants.MIN_TRF_AMOUNT) < 0) {
			throw new Exception("Minimum amount to withdraw is ".concat(Constants.MIN_TRF_AMOUNT.toString()));
		}
	}

	private void validateAccount(String acc) throws Exception {
		//validate acc length
		//todo: reactivate later
		/*if(acc.length() != Constants.ACC_LENGTH) {
			throw new Exception("Account Number should have 6 digits length");
		}*/

		//validate acc only number
		if(!acc.matches("[0-9]+")) {
			throw new Exception("Account Number should only contains numbers");
		}
	}

	private String generateRefNo() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	private TransactionDto convertToDto(Transaction trx, Account arc) {
		TransactionDto dto = new TransactionDto();
		dto.setId(trx.getId());
		dto.setDate(trx.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
		dto.setAmount(trx.getAmount().toString());
		dto.setBalance(arc.getBalance().toString());
		dto.setAccount(trx.getAccount());
		dto.setDestinationAccount(trx.getDestinationAccount());
		dto.setReferenceNumber(trx.getReferenceNumber());

		return dto;
	}
}
