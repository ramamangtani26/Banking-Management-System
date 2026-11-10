package com.rama.banking.exception;

import java.math.BigDecimal;

public class MaxBalanceException extends RuntimeException {
    public MaxBalanceException(String type, BigDecimal limit) {
        super("Exceeds maximum balance of " + limit.toPlainString() + " for " + type + " accounts");
    }
}
