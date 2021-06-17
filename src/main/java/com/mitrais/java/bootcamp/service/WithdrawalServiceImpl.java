package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Withdrawal;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class WithdrawalServiceImpl extends TransactionServiceAbstract {
    @Override
    public TransactionDto createTransaction(TransactionDto dto) throws Exception {
        Account src = accountService.findByAccount(dto.getAccount());

        validateAmountValue(dto.getAmount());
        BigDecimal amt = new BigDecimal(dto.getAmount());

        //create trx
        Withdrawal trx = new Withdrawal(LocalDateTime.now(), src, amt);
        validateTransaction(trx);

        //validate balance is enough
        if (src.getBalance().compareTo(amt) < 0){
            throw new Exception("Insufficient balance $".concat(amt.toString()));
        }

        //save unconfirmed trx in db
        trxRepo.save(trx);

        return convertToDto(trx);
    }

    @Override
    protected void validateTransaction(AbstractTransaction trx) throws Exception{
        super.validateTransaction(trx);

        //validate if withdraw amount is not multiple of $10.
        if (trx.getAmount().remainder(BigDecimal.TEN).compareTo(BigDecimal.ZERO) != 0) {
            throw new Exception("Invalid amount");
        }
    }
}
