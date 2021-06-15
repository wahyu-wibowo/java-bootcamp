package com.mitrais.java.bootcamp.model.persistence;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transfer extends AbstractTransaction {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="destination_account")
    private Account destinationAccount;
    private String referenceNumber;

    public Transfer() {
        //default
    }

    public Transfer(LocalDateTime transactionDate, Account account, BigDecimal amount, Account destinationAccount, String referenceNumber) {
        super(transactionDate, account, amount);
        this.destinationAccount = destinationAccount;
        this.referenceNumber = referenceNumber;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
