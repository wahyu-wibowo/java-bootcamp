package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Transfer;
import com.mitrais.java.bootcamp.model.persistence.Withdrawal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private AccountRepository accRepo;

	@Autowired
	private TransactionRepository trxRepo;

	@PersistenceContext
	private EntityManager entityManager;

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
		Optional<Account> result = accRepo.findAll().stream().filter(x -> x.getAccountNumber().equals(input.getAccountNumber())).findAny();
		if (!result.isPresent() || !result.get().getPin().equals(input.getPin())){
			throw new Exception("Invalid Account Number/PIN");
		}
	}

	@Override
	public Account findByAccount(String account) throws Exception {
		Optional<Account> result = accRepo.findAll().stream().filter(x -> x.getAccountNumber().equals(account)).findAny();
		if (!result.isPresent()){
			throw new Exception("Invalid Account: Account Not Found");
		} else {
			return result.get();
		}
	}

	@Override
	public TransactionDto createWithdrawal(String account, String amount) throws Exception {
		if(!amount.matches("[0-9]+([,.][0-9]{1,2})?")) {
			throw new Exception("Invalid amount: should only contains numbers");
		}

		Account acc = findByAccount(account);
		BigDecimal amt = new BigDecimal(amount);

		//create trx
		Withdrawal trx = new Withdrawal(LocalDateTime.now(), acc, amt);

		validateTransaction(trx);
		//validate if withdraw amount is not multiple of $10.
		if(trx.getAmount().remainder(BigDecimal.TEN).compareTo(BigDecimal.ZERO) != 0) {
			throw new Exception("Invalid amount");
		}

		//validate balance is enough
		if (acc.getBalance().compareTo(amt) < 0){
			throw new Exception("Insufficient balance $".concat(amt.toString()));
		}

		//save unconfirmed trx in db
		trxRepo.save(trx);

		return convertToDto(trx, acc);
	}

	@Override
	public TransactionDto createTransfer(String srcAcc, String dstAcc, String amount) throws Exception {
		Account src = findByAccount(srcAcc);

		validateAccount(dstAcc);
		Account	dst = findByAccount(dstAcc);

		if(!amount.matches("[0-9]+([,.][0-9]{1,2})?")) {
			throw new Exception("Invalid amount: should only contains numbers");
		}
		BigDecimal amt = new BigDecimal(amount);

		//create trx
		Transfer trx = new Transfer(LocalDateTime.now(), src, amt);
		trx.setDestinationAccount(dst);
		trx.setReferenceNumber(generateRefNo());
		validateTransfer(trx);

		//validate balance is enough
		if (src.getBalance().compareTo(amt) < 0){
			throw new Exception("Insufficient balance $".concat(amt.toString()));
		}

		//save unconfirmed trx in db
		trxRepo.save(trx);

		return convertToDto(trx, src);
	}

	@Override
	public AbstractTransaction confirmTransaction(String id) throws Exception {
		//saving trx and do journaling
		//todo: might need to check trx confirmation status
		AbstractTransaction trx = trxRepo.findOne(Long.valueOf(id));
		if (trx == null || trx.getAmount() == null){
			throw new Exception("Transaction Not Found");
		} else {
			Account acc = trx.getAccount();

			//revalidate balance is enough
			if (acc.getBalance().compareTo(trx.getAmount()) < 0){
				throw new Exception("Insufficient balance $".concat(trx.getAmount().toString()));
			}

			//journaling
			//todo: try
			Transfer trf = (Transfer) trx;
			if (!StringUtils.isEmpty(trf.getDestinationAccount())) {
				//transfer journal
				Account destAcc = trf.getDestinationAccount();
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

	@Override
	public List<TransactionDto> inquiryTransaction(TransactionDto transactionDto) throws Exception {
		if (!StringUtils.isEmpty(transactionDto.getAccount())) {
			return convertToDto(findOrderedBySeatNumberLimitedTo(transactionDto.getAccount()));
		} else if (!StringUtils.isEmpty(transactionDto.getDate())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy");
			LocalDate dateTime = LocalDate.parse(transactionDto.getDate(), formatter);

			LocalDateTime before = dateTime.atStartOfDay();
			LocalDateTime after = dateTime.atTime(23,59, 59);
			return convertToDto(trxRepo.findByTransactionDateBetweenAndIsConfirmedTrueOrderByTransactionDateDesc(before, after));
		}
		return null;
	}

	private void validateTransaction(AbstractTransaction trx) throws Exception{
		//validate max amount per transaction
		if (trx.getAmount().compareTo(Constants.MAX_TRX_AMOUNT) > 0) {
			throw new Exception("Maximum amount to withdraw is $".concat(Constants.MAX_TRX_AMOUNT.toString()));
		}
	}

	private void validateTransfer(AbstractTransaction trx) throws Exception{
		validateTransaction(trx);

		//validate if transfer amount is < 1
		if (trx.getAmount().compareTo(Constants.MIN_TRF_AMOUNT) < 0) {
			throw new Exception("Minimum amount to withdraw is ".concat(Constants.MIN_TRF_AMOUNT.toString()));
		}
	}

	private void validateAccount(String acc) throws Exception {
		//validate acc only number
		if(!acc.matches("[0-9]+")) {
			throw new Exception("Invalid Account: Account Number should only contains numbers");
		}

		//validate acc length
		if(acc.length() != Constants.ACC_LENGTH) {
			throw new Exception("Account Number should have 6 digits length");
		}
	}

	private String generateRefNo() {
		Random rnd = new Random();
		int number = rnd.nextInt(999999);
		return String.format("%06d", number);
	}

	private TransactionDto convertToDto(AbstractTransaction trx, Account arc) {
		TransactionDto dto = new TransactionDto();
		dto.setId(trx.getId());
		dto.setDate(trx.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
		dto.setAmount(trx.getAmount().toString());
		dto.setBalance(arc.getBalance().subtract(trx.getAmount()).toString());
		dto.setAccount(trx.getAccount().getAccountNumber());
		//todo: try
		/*dto.setDestinationAccount(trx.getDestinationAccount());
		dto.setReferenceNumber(trx.getReferenceNumber());*/

		return dto;
	}

	private List<TransactionDto> convertToDto(List<AbstractTransaction> trxs) {
		List<TransactionDto> result = new ArrayList<>();
		trxs.stream().forEach(trx -> {
			TransactionDto dto = new TransactionDto();
			dto.setId(trx.getId());
			dto.setDate(trx.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
			dto.setAmount(trx.getAmount().toString());
			dto.setAccount(trx.getAccount().getAccountNumber());
			//todo: try
			/*dto.setDestinationAccount(trx.getDestinationAccount());
			dto.setReferenceNumber(trx.getReferenceNumber());*/

			result.add(dto);
		});

		return result;
	}

	private List<AbstractTransaction> findOrderedBySeatNumberLimitedTo(String acc) {
		return entityManager.createQuery("SELECT tx FROM AbstractTransaction tx WHERE tx.account = :acc AND tx.isConfirmed = true ORDER BY tx.transactionDate desc ", AbstractTransaction.class)
				.setParameter("acc", acc)
				.setMaxResults(Constants.MAX_QUERY_BY_ACC_LIMIT)
				.getResultList();
	}
}
