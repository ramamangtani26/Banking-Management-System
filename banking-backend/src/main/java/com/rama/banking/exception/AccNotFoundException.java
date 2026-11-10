package com.rama.banking.exception;

public class AccNotFoundException extends RuntimeException {
    public AccNotFoundException(Long accountNumber) {
        super("Account not found: " + accountNumber);
    }
}
