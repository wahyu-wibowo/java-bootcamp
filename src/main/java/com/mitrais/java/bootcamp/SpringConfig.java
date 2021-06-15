package com.mitrais.java.bootcamp;

import com.mitrais.java.bootcamp.service.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public TransferServiceImpl getTransactionService() {
        return new TransferServiceImpl();
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
