package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferServiceImpl extends TransactionServiceAbstract {
    //todo: can be simplified
    @Override
    public TransactionDto createTransaction(TransactionDto dto) throws Exception {
        Account src = accountService.findByAccount(dto.getAccount());

        accountService.validateAccount(dto.getDestinationAccount());
        Account	dst = accountService.findByAccount(dto.getDestinationAccount());

        if(!dto.getAmount().matches("[0-9]+([,.][0-9]{1,2})?")) {
            throw new Exception("Invalid amount: should only contains numbers");
        }
        BigDecimal amt = new BigDecimal(dto.getAmount());

        //create trx
        Transfer trx = new Transfer(LocalDateTime.now(), src, amt, dst, generateRefNo());
        validateTransfer(trx);

        //validate balance is enough
        if (src.getBalance().compareTo(amt) < 0){
            throw new Exception("Insufficient balance $".concat(amt.toString()));
        }

        //save unconfirmed trx in db
        trxRepo.save(trx);

        return convertToDto(trx);
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
