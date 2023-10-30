package com.example.banking_manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Entity
@Table(name="deposits")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="customer_id",referencedColumnName = "id",nullable = false)
    private Customer customerDeposit;
    @NotNull
    private BigDecimal transactionAmount;
    private Boolean deleted;

}
