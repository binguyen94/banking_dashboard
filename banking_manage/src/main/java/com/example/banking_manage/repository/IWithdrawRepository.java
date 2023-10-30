package com.example.banking_manage.repository;

import com.example.banking_manage.model.Withdraw;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;


public interface IWithdrawRepository extends JpaRepository<Withdraw,Long> {
}
