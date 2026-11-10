package com.rama.banking.controller;

import com.rama.banking.dto.AccountResponse;
import com.rama.banking.dto.AmountRequest;
import com.rama.banking.dto.CreateAccountRequest;
import com.rama.banking.dto.TransactionResponse;
import com.rama.banking.service.AccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse create(@Valid @RequestBody CreateAccountRequest request) {
        return service.create(request);
    }

    @GetMapping
    public List<AccountResponse> list(@RequestParam(required = false) String search) {
        return service.list(search);
    }

    @GetMapping("/{accountNumber}")
    public AccountResponse get(@PathVariable Long accountNumber) {
        return service.get(accountNumber);
    }

    @PostMapping("/{accountNumber}/deposit")
    public TransactionResponse deposit(@PathVariable Long accountNumber,
                                       @Valid @RequestBody AmountRequest request) {
        return service.deposit(accountNumber, request.amount());
    }

    @PostMapping("/{accountNumber}/withdraw")
    public TransactionResponse withdraw(@PathVariable Long accountNumber,
                                        @Valid @RequestBody AmountRequest request) {
        return service.withdraw(accountNumber, request.amount());
    }

    @GetMapping("/{accountNumber}/transactions")
    public List<TransactionResponse> history(@PathVariable Long accountNumber) {
        return service.history(accountNumber);
    }
}
