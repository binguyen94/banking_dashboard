package com.example.banking_manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="deposits")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customerDeposit;

    @Column(name = "transaction_amount", columnDefinition = "decimal(10,0)", nullable = false)
    private BigDecimal transactionAmount;
    private Boolean deleted;

    private LocalDateTime createAt;

    public Deposit() {
    }

    public Deposit(Long id, Customer customerDeposit, BigDecimal transactionAmount, Boolean deleted, LocalDateTime createAt) {
        this.id = id;
        this.customerDeposit = customerDeposit;
        this.transactionAmount = transactionAmount;
        this.deleted = deleted;
        this.createAt = createAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomerDeposit() {
        return customerDeposit;
    }

    public void setCustomerDeposit(Customer customerDeposit) {
        this.customerDeposit = customerDeposit;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public String getCreateAt() {
        String inputDateTime = String.valueOf(createAt);
        LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, DateTimeFormatter.ISO_DATE_TIME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return dateTime.format(formatter);
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
