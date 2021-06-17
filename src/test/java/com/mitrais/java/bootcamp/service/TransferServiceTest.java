package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Transfer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TransferServiceTest {
    @Mock
    protected AccountService accountService;

    @Mock
    private TransactionRepository trxRepo;

    @Mock
    private AccountRepository accRepo;

    @InjectMocks
    TransferServiceImpl service = new TransferServiceImpl();

    @Test
    public void createTransactionTest() throws Exception {
        Account srcAcc = new Account();
        srcAcc.setAccountNumber("112255");
        srcAcc.setPin("112255");
        srcAcc.setBalance(new BigDecimal(100));

        Account dstAcc = new Account();
        dstAcc.setAccountNumber("112244");
        dstAcc.setPin("112255");
        dstAcc.setBalance(BigDecimal.ZERO);

        TransactionDto request = new TransactionDto();
        request.setAccount(srcAcc.getAccountNumber());
        request.setDestinationAccount(dstAcc.getAccountNumber());
        request.setAmount("10");

        when(accountService.findByAccount(srcAcc.getAccountNumber())).thenReturn(srcAcc);
        when(accountService.findByAccount(dstAcc.getAccountNumber())).thenReturn(dstAcc);
        TransactionDto result = service.createTransaction(request);
        assertEquals("90", result.getBalance());

        Transfer transfer = new Transfer(LocalDateTime.now(), srcAcc, new BigDecimal(result.getAmount()), dstAcc, result.getReferenceNumber());
        when(trxRepo.findOne(result.getId())).thenReturn(transfer);
        Transfer trf = (Transfer) service.confirmTransaction(result.getId().toString());
        assertEquals(new BigDecimal(90), trf.getAccount().getBalance());
        assertEquals(new BigDecimal(10), trf.getDestinationAccount().getBalance());
    }

    @Test
    public void createTransactionTest_fail() throws Exception {
        Account srcAcc = new Account();
        srcAcc.setAccountNumber("112255");
        srcAcc.setPin("112255");
        srcAcc.setBalance(new BigDecimal(100));

        TransactionDto request = new TransactionDto();
        request.setAccount(srcAcc.getAccountNumber());
        request.setDestinationAccount(srcAcc.getAccountNumber());
        request.setAmount("900");

        when(accountService.findByAccount(anyString())).thenReturn(srcAcc);
        Exception exBalance = assertThrows(Exception.class, () -> service.createTransaction(request));
        String balanceMessage = "Insufficient balance $900";
        assertEquals(balanceMessage, exBalance.getMessage());

        request.setAmount("ab");
        Exception exNumber = assertThrows(Exception.class, () -> service.createTransaction(request));
        String numberMessage = "Invalid amount: should only contains numbers";
        assertEquals(numberMessage, exNumber.getMessage());

        request.setAmount("0.1");
        Exception exMin = assertThrows(Exception.class, () -> service.createTransaction(request));
        String minMessage = "Minimum amount to withdraw is 1";
        assertEquals(minMessage, exMin.getMessage());
    }
}
