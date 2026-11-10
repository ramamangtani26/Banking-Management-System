package com.rama.banking.service;

import com.rama.banking.dto.AccountResponse;
import com.rama.banking.dto.CreateAccountRequest;
import com.rama.banking.dto.TransactionResponse;
import com.rama.banking.exception.*;
import com.rama.banking.model.Account;
import com.rama.banking.model.BankTransaction;
import com.rama.banking.model.TransactionType;
import com.rama.banking.repository.AccountRepository;
import com.rama.banking.repository.TransactionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class AccountService {

    private final AccountRepository accounts;
    private final TransactionRepository transactions;

    public AccountService(AccountRepository accounts, TransactionRepository transactions) {
        this.accounts = accounts;
        this.transactions = transactions;
    }

    @Transactional
    public AccountResponse create(CreateAccountRequest req) {
        BigDecimal opening = req.openingBalance().setScale(2, RoundingMode.HALF_UP);
        if (opening.compareTo(req.type().getMaxBalance()) > 0) {
            throw new MaxBalanceException(req.type().name(), req.type().getMaxBalance());
        }
        Account saved = accounts.save(new Account(req.holderName().trim(), req.type(), opening));
        if (opening.signum() > 0) {
            record(saved, TransactionType.OPENING, opening);
        }
        return AccountResponse.from(saved);
    }

    @Transactional(readOnly = true)
    public AccountResponse get(Long accountNumber) {
        return AccountResponse.from(find(accountNumber));
    }

    /** Lists all accounts, or searches by holder name / account number. */
    @Transactional(readOnly = true)
    public List<AccountResponse> list(String search) {
        List<Account> result;
        if (search == null || search.isBlank()) {
            result = accounts.findAll(Sort.by("accountNumber"));
        } else if (search.trim().matches("\\d{1,18}")) {
            result = accounts.findById(Long.parseLong(search.trim())).map(List::of).orElse(List.of());
        } else {
            result = accounts.findByHolderNameContainingIgnoreCaseOrderByAccountNumber(search.trim());
        }
        return result.stream().map(AccountResponse::from).toList();
    }

    @Transactional
    public TransactionResponse deposit(Long accountNumber, BigDecimal rawAmount) {
        BigDecimal amount = validAmount(rawAmount);
        Account a = lock(accountNumber);
        BigDecimal newBalance = a.getBalance().add(amount);
        if (newBalance.compareTo(a.getType().getMaxBalance()) > 0) {
            throw new MaxBalanceException(a.getType().name(), a.getType().getMaxBalance());
        }
        a.setBalance(newBalance);
        return TransactionResponse.from(record(a, TransactionType.DEPOSIT, amount));
    }

    @Transactional
    public TransactionResponse withdraw(Long accountNumber, BigDecimal rawAmount) {
        BigDecimal amount = validAmount(rawAmount);
        Account a = lock(accountNumber);
        if (amount.compareTo(a.getType().getMaxWithdraw()) > 0) {
            throw new MaxWithdrawException(a.getType().name(), a.getType().getMaxWithdraw());
        }
        if (amount.compareTo(a.getBalance()) > 0) {
            throw new InsufficientFundsException(a.getBalance());
        }
        a.setBalance(a.getBalance().subtract(amount));
        return TransactionResponse.from(record(a, TransactionType.WITHDRAW, amount));
    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> history(Long accountNumber) {
        find(accountNumber); // 404 if the account doesn't exist
        return transactions.findByAccountNumberOrderByIdDesc(accountNumber)
                .stream().map(TransactionResponse::from).toList();
    }

    // ---- helpers ----
    private Account find(Long no) {
        return accounts.findById(no).orElseThrow(() -> new AccNotFoundException(no));
    }

    private Account lock(Long no) {
        return accounts.findByIdForUpdate(no).orElseThrow(() -> new AccNotFoundException(no));
    }

    private BigDecimal validAmount(BigDecimal amount) {
        if (amount == null) throw new InvalidAmountException();
        BigDecimal scaled = amount.setScale(2, RoundingMode.HALF_UP);
        if (scaled.signum() <= 0) throw new InvalidAmountException();
        return scaled;
    }

    private BankTransaction record(Account a, TransactionType type, BigDecimal amount) {
        return transactions.save(new BankTransaction(a.getAccountNumber(), type, amount, a.getBalance()));
    }
}
