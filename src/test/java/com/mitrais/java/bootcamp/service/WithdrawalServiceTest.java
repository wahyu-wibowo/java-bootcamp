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
import java.util.ArrayList;
import java.util.List;

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
    public void generateRefNoTest() {
        String refNo = service.generateRefNo();

        //validate reference number only contain number and its length is 6 digit
        assertTrue(refNo.matches("[0-9]+") && refNo.length() == 6);
    }

    @Test
    public void createTransactionTest() throws Exception {
        //first transaction
        Withdrawal wd1 = createAndConfirmTransaction(generateAccount(), "10", "90");

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

        //sixth transaction - non multiply of 10
        Exception exNumber = assertThrows(Exception.class, () -> createAndConfirmTransaction(wd2.getAccount(), "ab", "0"));
        String numberMessage = "Invalid amount: should only contains numbers";
        assertEquals(numberMessage, exNumber.getMessage());

        //seventh transaction - non multiply of 10
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

    private List<Account> generateAccounts() {
        List<Account> result = new ArrayList<>();

        Account acc1 = new Account();
        acc1.setAccountNumber("112233");
        acc1.setPin("123456");
        acc1.setBalance(new BigDecimal(100));
        result.add(acc1);

        Account acc2 = new Account();
        acc2.setAccountNumber("112244");
        acc2.setPin("123455");
        result.add(acc2);

        result.add(generateAccount());

        return result;
    }

    private Account generateAccount() {
        Account account = new Account();
        account.setAccountNumber("112255");
        account.setPin("112255");
        account.setBalance(new BigDecimal(100));

        return account;
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
