package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.Constants;
import com.mitrais.java.bootcamp.model.dto.TransactionDto;
import com.mitrais.java.bootcamp.model.persistence.Account;
import com.mitrais.java.bootcamp.model.persistence.Withdrawal;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class WithdrawalServiceTest {
    @Mock
    protected AccountService accountService;

    @Mock
    private TransactionRepository trxRepo;

    @Mock
    private AccountRepository accRepo;

    @InjectMocks
    WithdrawalServiceImpl service = new WithdrawalServiceImpl();

    @Test
    public void createTransactionTest() throws Exception {
        Account account = new Account();
        account.setAccountNumber("112255");
        account.setPin("112255");
        account.setBalance(new BigDecimal(100));

        //first transaction
        Withdrawal wd1 = createAndConfirmTransaction(account, "10", "90");

        //second transaction
        Withdrawal wd2 = createAndConfirmTransaction(wd1.getAccount(), "50", "40");

        //third transaction - fail insufficient
        Exception exInsuf = assertThrows(Exception.class, () -> createAndConfirmTransaction(wd2.getAccount(), "100", "0"));
        String insufMessage = "Insufficient balance $100";
        assertEquals(insufMessage, exInsuf.getMessage());

        //fourth transaction - fail insufficient 2
        Exception exInsuf2 = assertThrows(Exception.class, () -> createAndConfirmTransaction(wd2.getAccount(), "50", "0"));
        String insufMessage2 = "Insufficient balance $50";
        assertEquals(insufMessage2, exInsuf2.getMessage());

        //fifth transaction - non multiply of 10
        Exception exMultiply = assertThrows(Exception.class, () -> createAndConfirmTransaction(wd2.getAccount(), "15", "0"));
        String multiplyMessage = "Invalid amount";
        assertEquals(multiplyMessage, exMultiply.getMessage());

        //sixth transaction - non number
        Exception exNumber = assertThrows(Exception.class, () -> createAndConfirmTransaction(wd2.getAccount(), "ab", "0"));
        String numberMessage = "Invalid amount: should only contains numbers";
        assertEquals(numberMessage, exNumber.getMessage());

        //seventh transaction - max amount
        Exception exBig = assertThrows(Exception.class, () -> createAndConfirmTransaction(wd2.getAccount(), "1500", "0"));
        String bigMessage = "Maximum amount to withdraw is $".concat(Constants.MAX_TRX_AMOUNT.toString());
        assertEquals(bigMessage, exBig.getMessage());

        //eight transaction
        createAndConfirmTransaction(wd2.getAccount(), "10", "30");
    }

    @Test
    public void createTransactionTest_fail() {
        Exception exNumber = assertThrows(Exception.class, () -> service.createTransaction(new TransactionDto("112233", "ab")));
        String numberMessage = "Invalid amount: should only contains numbers";
        assertEquals(numberMessage, exNumber.getMessage());
    }

    private Withdrawal createAndConfirmTransaction(Account acc, String amount, String expectedAfterBalance) throws Exception {
        when(accountService.findByAccount(anyString())).thenReturn(acc);
        TransactionDto result = service.createTransaction(new TransactionDto(acc.getAccountNumber(), amount));
        assertEquals(expectedAfterBalance, result.getBalance());

        Withdrawal withdrawal = new Withdrawal(LocalDateTime.now(), acc, new BigDecimal(amount));
        when(trxRepo.findOne(result.getId())).thenReturn(withdrawal);
        return  (Withdrawal) service.confirmTransaction(result.getId().toString());
    }
}
