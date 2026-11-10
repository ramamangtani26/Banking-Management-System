package com.rama.banking.exception;

import java.math.BigDecimal;

public class InsufficientFundsException extends RuntimeException {
    public InsufficientFundsException(BigDecimal balance) {
        super("Insufficient funds. Current balance: " + balance.toPlainString());
    }
}
