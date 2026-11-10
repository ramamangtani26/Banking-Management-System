package com.rama.banking.dto;

import com.rama.banking.model.BankTransaction;
import com.rama.banking.model.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponse(Long id, Long accountNumber, TransactionType type,
                                  BigDecimal amount, BigDecimal balanceAfter, LocalDateTime createdAt) {
    public static TransactionResponse from(BankTransaction t) {
        return new TransactionResponse(t.getId(), t.getAccountNumber(), t.getType(),
                t.getAmount(), t.getBalanceAfter(), t.getCreatedAt());
    }
}
