package com.mitrais.java.bootcamp.model.persistence;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction")
public class Transfer extends AbstractTransaction {
    // for fund transfer only
    private String destinationAccount;
    private String referenceNumber;

    public Transfer() {
        //default
    }

    public Transfer(LocalDateTime transactionDate, String account, BigDecimal amount) {
        super(transactionDate, account, amount);
    }

    public String getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(String destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getReferenceNumber() {
        return referenceNumber;
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber = referenceNumber;
    }
}
