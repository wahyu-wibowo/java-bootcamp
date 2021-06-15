package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.AbstractTransaction;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransferServiceImpl extends TransactionServiceAbstract {
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
        Transfer transfer = (Transfer) trx;
        TransactionDto dto = super.convertToDto(trx);
        dto.setDestinationAccount(transfer.getDestinationAccount().getAccountNumber());
		dto.setReferenceNumber(transfer.getReferenceNumber());

        return dto;
    }

    @Override
    //todo: might not needed
    protected List<TransactionDto> convertToDto(List<AbstractTransaction> trxs) {
        List<TransactionDto> result = new ArrayList<>();
        trxs.stream().forEach(trx -> {
            result.add(convertToDto(trx));
        });

        return result;
    }
}
