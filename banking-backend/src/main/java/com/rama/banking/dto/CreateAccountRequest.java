package com.rama.banking.dto;

import com.rama.banking.model.AccountType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateAccountRequest(
        @NotBlank(message = "Holder name is required") String holderName,
        @NotNull(message = "Account type is required (SAVINGS, CURRENT or STUDENT)") AccountType type,
        @NotNull(message = "Opening balance is required")
        @DecimalMin(value = "0.0", message = "Opening balance must be 0 or more") BigDecimal openingBalance
) { }
