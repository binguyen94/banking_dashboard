package com.example.banking_manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
@Entity
@Table(name="withdraws")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Withdraw {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="customer_id",referencedColumnName = "id",nullable = false)
    private Customer customerWithdraws;
    private BigDecimal transactionAmount;
    private Boolean deleted;

}
