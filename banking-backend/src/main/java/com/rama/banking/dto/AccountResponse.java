package com.rama.banking.dto;

import com.rama.banking.model.Account;
import com.rama.banking.model.AccountType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record AccountResponse(Long accountNumber, String holderName, AccountType type,
                              BigDecimal balance, LocalDateTime createdAt) {
    public static AccountResponse from(Account a) {
        return new AccountResponse(a.getAccountNumber(), a.getHolderName(), a.getType(),
                a.getBalance(), a.getCreatedAt());
    }
}
