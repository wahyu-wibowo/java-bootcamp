package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Account;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class TransactionServiceAbstract implements TransactionService {
    @Autowired
    protected AccountRepository accRepo;

    @Autowired
    protected TransactionRepository trxRepo;

    @PersistenceContext
    protected EntityManager entityManager;

    @Autowired
    protected AccountService accountService;

    /*@Override
    public TransactionDto createTransaction(TransactionDto dto) throws Exception {
        String account = dto.getAccount();
        String amount = dto.getAmount();
        if(!amount.matches("[0-9]+([,.][0-9]{1,2})?")) {
            throw new Exception("Invalid amount: should only contains numbers");
        }

        Account acc = accountService.findByAccount(account);
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

        return convertToDto(trx);
    }*/

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

    protected void validateTransaction(AbstractTransaction trx) throws Exception{
        //validate max amount per transaction
        if (trx.getAmount().compareTo(Constants.MAX_TRX_AMOUNT) > 0) {
            throw new Exception("Maximum amount to withdraw is $".concat(Constants.MAX_TRX_AMOUNT.toString()));
        }
    }

    protected void validateTransfer(AbstractTransaction trx) throws Exception{
        validateTransaction(trx);

        //validate if transfer amount is < 1
        if (trx.getAmount().compareTo(Constants.MIN_TRF_AMOUNT) < 0) {
            throw new Exception("Minimum amount to withdraw is ".concat(Constants.MIN_TRF_AMOUNT.toString()));
        }
    }

    protected String generateRefNo() {
        Random rnd = new Random();
        int number = rnd.nextInt(999999);
        return String.format("%06d", number);
    }

    protected TransactionDto convertToDto(AbstractTransaction trx) {
        TransactionDto dto = new TransactionDto();
        dto.setId(trx.getId());
        dto.setDate(trx.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        dto.setAmount(trx.getAmount().toString());
        dto.setBalance(trx.getAccount().getBalance().subtract(trx.getAmount()).toString());
        dto.setAccount(trx.getAccount().getAccountNumber());

        return dto;
    }

    protected TransactionDto convertToDto(Transfer trx) {
        TransactionDto dto = new TransactionDto();
        dto.setId(trx.getId());
        dto.setDate(trx.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        dto.setAmount(trx.getAmount().toString());
        dto.setBalance(trx.getAccount().getBalance().subtract(trx.getAmount()).toString());
        dto.setAccount(trx.getAccount().getAccountNumber());

        //transfer journal
        Account destAcc = trx.getDestinationAccount();
        destAcc.setBalance(destAcc.getBalance().add(trx.getAmount()));
        accRepo.save(destAcc);

        return dto;
    }

    protected TransactionDto convertToDto(Withdrawal trx) {
        TransactionDto dto = new TransactionDto();
        dto.setId(trx.getId());
        dto.setDate(trx.getTransactionDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a")));
        dto.setAmount(trx.getAmount().toString());
        dto.setBalance(trx.getAccount().getBalance().subtract(trx.getAmount()).toString());
        dto.setAccount(trx.getAccount().getAccountNumber());

        return dto;
    }

    protected List<TransactionDto> convertToDto(List<AbstractTransaction> trxs) {
        List<TransactionDto> result = new ArrayList<>();
        trxs.stream().forEach(trx -> result.add(convertToDto(trx)));

        return result;
    }

    protected List<AbstractTransaction> findOrderedBySeatNumberLimitedTo(String acc) {
        return entityManager.createQuery("SELECT tx FROM AbstractTransaction tx WHERE tx.account.accountNumber = :acc AND tx.isConfirmed = true ORDER BY tx.transactionDate desc ", AbstractTransaction.class)
                .setParameter("acc", acc)
                .setMaxResults(Constants.MAX_QUERY_BY_ACC_LIMIT)
                .getResultList();
    }
}
