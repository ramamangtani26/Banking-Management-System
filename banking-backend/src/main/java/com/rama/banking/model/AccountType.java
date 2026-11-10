package com.rama.banking.model;

import java.math.BigDecimal;

/** Business rules per account type (same limits as the web demo). */
public enum AccountType {
    SAVINGS(new BigDecimal("1000000"), new BigDecimal("50000")),
    CURRENT(new BigDecimal("5000000"), new BigDecimal("200000")),
    STUDENT(new BigDecimal("50000"), new BigDecimal("5000"));

    private final BigDecimal maxBalance;
    private final BigDecimal maxWithdraw;

    AccountType(BigDecimal maxBalance, BigDecimal maxWithdraw) {
        this.maxBalance = maxBalance;
        this.maxWithdraw = maxWithdraw;
    }

    public BigDecimal getMaxBalance() { return maxBalance; }
    public BigDecimal getMaxWithdraw() { return maxWithdraw; }
}
