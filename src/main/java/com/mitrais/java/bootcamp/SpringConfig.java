package com.mitrais.java.bootcamp;

import com.mitrais.java.bootcamp.service.TransactionService;
import com.mitrais.java.bootcamp.service.TransactionServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public TransactionService getTransactionService() {
        return new TransactionServiceImpl();
    }

}
