package com.mitrais.java.bootcamp;

import com.mitrais.java.bootcamp.service.AccountService;
import com.mitrais.java.bootcamp.service.AccountServiceImpl;
import com.mitrais.java.bootcamp.service.TransactionService;
import com.mitrais.java.bootcamp.service.WithdrawalServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public TransactionService getTransactionService() {
        return new WithdrawalServiceImpl();
    }

    @Bean
    public AccountService getAccountService() {
        return new AccountServiceImpl();
    }

    @Bean
    public WithdrawalServiceImpl getWithdrawalServiceImpl() {
        return new WithdrawalServiceImpl();
    }

}
