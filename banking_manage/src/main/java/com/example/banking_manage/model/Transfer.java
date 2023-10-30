package com.example.banking_manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
@Entity
@Table(name="transfers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name="sender_id",referencedColumnName = "id",nullable = false)
    private Customer sender;
    @ManyToOne
    @JoinColumn(name="recipient_id",referencedColumnName = "id",nullable = false)
    private Customer recipient;
    @NotNull
    private BigDecimal transferAmount;
    @NotNull
    private Long fees;
    @NotNull
    private BigDecimal feesAmount;
    @NotNull
    private BigDecimal transactionAmount;
    private Boolean deleted;



}
