package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Withdrawal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawalServiceImpl extends TransactionServiceAbstract {
    //todo: can be simplified
    @Override
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
    }
}
