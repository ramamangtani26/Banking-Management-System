package com.rama.banking.repository;

import com.rama.banking.model.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    List<Account> findByHolderNameContainingIgnoreCaseOrderByAccountNumber(String name);

    /** Row lock so two simultaneous withdrawals can't overdraw an account. */
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where a.accountNumber = :no")
    Optional<Account> findByIdForUpdate(@Param("no") Long no);
}
