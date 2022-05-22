package com.example.midtermbankingsystem.repository;

import com.example.midtermbankingsystem.model.Account;
import com.example.midtermbankingsystem.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByPayerAccOrderByTransferDate(Account account);


    @Query(value = "SELECT SUM(amount) FROM transaction\n" +
            "WHERE CAST(transfer_date AS DATE ) != current_date AND payer_acc_id = :account\n" +
            "GROUP BY CAST(transfer_date AS DATE ), payer_acc_id ORDER BY SUM(amount) DESC  LIMIT 1",
            nativeQuery = true)
    BigDecimal findHighestDailyTransactionByAccount(Account account);

    @Query(value = "SELECT SUM(amount) FROM transaction\n" +
            "WHERE CAST(transfer_date AS DATE) = current_date AND payer_acc_id = :account",
            nativeQuery = true)
    BigDecimal findTodayTotalTransactions(Account account);
}
