package com.mitrais.java.bootcamp.service;

import com.mitrais.java.bootcamp.model.persistence.Account;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class AccountServiceTest {
    @Mock
    private AccountRepository accRepo;

    @InjectMocks
    AccountService service = new AccountServiceImpl();

    public void before() {
        Account account = new Account();
        account.setAccountNumber("112233");
        account.setPin("112233");
    }

    @Test
    public void checkAuthTest() throws Exception {
        Account acc = new Account();
        acc.setAccountNumber("112233");
        acc.setPin("123456");
        when(accRepo.findAll()).thenReturn(generateAccounts());
        service.checkAuth(acc);
    }

    @Test
    public void checkAuthTest_fail_account() {
        Account acc = new Account();
        acc.setAccountNumber("11");

        Exception exLength = assertThrows(Exception.class, () -> service.checkAuth(acc));

        acc.setAccountNumber("1122ab");
        Exception exNumber = assertThrows(Exception.class, () -> service.checkAuth(acc));

        String lengthMessage = "Account Number should have 6 digits length";
        String numberMessage = "Invalid Account: Account Number should only contains numbers";

        assertEquals(lengthMessage, exLength.getMessage());
        assertEquals(numberMessage, exNumber.getMessage());
    }

    @Test
    public void checkAuthTest_fail_pin() {
        Account acc = new Account();
        acc.setAccountNumber("112233");
        acc.setPin("11");

        Exception exLength = assertThrows(Exception.class, () -> service.checkAuth(acc));

        acc.setPin("1122ab");
        Exception exNumber = assertThrows(Exception.class, () -> service.checkAuth(acc));

        acc.setPin("112255");
        Exception exNotFound = assertThrows(Exception.class, () -> service.checkAuth(acc));

        acc.setPin("112233");
        when(accRepo.findAll()).thenReturn(generateAccounts());
        Exception exUnmatch = assertThrows(Exception.class, () -> service.checkAuth(acc));

        String lengthMessage = "PIN should have 6 digits length";
        String numberMessage = "PIN should only contains numbers";
        String unmatchMessage = "Invalid Account Number/PIN";

        assertEquals(lengthMessage, exLength.getMessage());
        assertEquals(numberMessage, exNumber.getMessage());
        assertEquals(unmatchMessage, exNotFound.getMessage());
        assertEquals(unmatchMessage, exUnmatch.getMessage());
    }

    @Test
    public void getAllAccountTest() {
        service.getAllAccount();
    }

    @Test
    public void findByAccountTest() throws Exception {
        when(accRepo.findAll()).thenReturn(generateAccounts());
        service.findByAccount("112233");
    }

    @Test
    public void findByAccountTest_fail() throws Exception {
        Exception exNotFound = assertThrows(Exception.class, () -> service.findByAccount("112235"));
        String notFoundMessage = "Invalid Account: Account Not Found";
        assertEquals(notFoundMessage, exNotFound.getMessage());
        when(accRepo.findAll()).thenReturn(generateAccounts());
    }

    private List<Account> generateAccounts() {
        List<Account> result = new ArrayList<>();

        Account acc1 = new Account();
        acc1.setAccountNumber("112233");
        acc1.setPin("123456");
        result.add(acc1);

        Account acc2 = new Account();
        acc2.setAccountNumber("112244");
        acc2.setPin("123455");
        result.add(acc2);

        return result;
    }
}
