package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferServiceImpl extends TransactionServiceAbstract {
    @Override
    public TransactionDto createTransaction(TransactionDto dto) throws Exception {
        accountService.validateAccount(dto.getDestinationAccount());
        Account	dst = accountService.findByAccount(dto.getDestinationAccount());

        Account src = accountService.findByAccount(dto.getAccount());

        validateAmountValue(dto.getAmount());
        BigDecimal amt = new BigDecimal(dto.getAmount());

        //create trx
        Transfer trx = new Transfer(LocalDateTime.now(), src, amt, dst, generateRefNo());
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

        //validate if transfer amount is < 1
        if (trx.getAmount().compareTo(Constants.MIN_TRF_AMOUNT) < 0) {
            throw new Exception("Minimum amount to withdraw is ".concat(Constants.MIN_TRF_AMOUNT.toString()));
        }
    }

    @Override
    public AbstractTransaction confirmTransaction(String id) throws Exception {
        Transfer trf = (Transfer) super.confirmTransaction(id);

        //transfer journal
        Account destAcc = trf.getDestinationAccount();
        destAcc.setBalance(destAcc.getBalance().add(trf.getAmount()));
        accRepo.save(destAcc);

        return trf;
    }

    @Override
    protected TransactionDto convertToDto(AbstractTransaction trx) {
        TransactionDto dto = super.convertToDto(trx);

        //transfer journal
        Transfer trf = (Transfer) trx;
        dto.setDestinationAccount(trf.getDestinationAccount().getAccountNumber());
        dto.setReferenceNumber(trf.getReferenceNumber());

        return dto;
    }
}
