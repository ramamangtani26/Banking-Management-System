package com.rama.banking.exception;

import java.math.BigDecimal;

public class MaxWithdrawException extends RuntimeException {
    public MaxWithdrawException(String type, BigDecimal limit) {
        super("Maximum withdrawal for " + type + " accounts is " + limit.toPlainString());
    }
}
