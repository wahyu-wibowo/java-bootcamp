package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Account;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.format.DateTimeFormatter;
import java.util.Random;

public abstract class TransactionServiceAbstract implements TransactionService {
    @Autowired
    protected AccountRepository accRepo;

    @Autowired
    protected TransactionRepository trxRepo;

    @Autowired
    protected AccountService accountService;

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

}
