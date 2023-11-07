package com.example.banking_manage.repository;

import com.example.banking_manage.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;


public interface IWithdrawRepository extends JpaRepository<Withdraw,Long> {
    @Modifying
    @Query("UPDATE Customer AS cus " +
            "SET cus.balance = cus.balance - :transactionAmount " +
            "WHERE cus.id = :customerId"
    )
    void decrementBalance(@Param("customerId") Long customerId, @Param("transactionAmount") BigDecimal transactionAmount);
}
