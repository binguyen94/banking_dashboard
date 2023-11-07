package com.example.banking_manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="withdraws")
public class Withdraw {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customerWithdraws;

    @Column(name = "transaction_amount", precision = 10, scale = 0, nullable = false)
    private BigDecimal transactionAmount;

    private Boolean deleted = false;

    private LocalDateTime createAt;

    public Withdraw() {
    }

    public Withdraw(Long id, Customer customerWithdraws, BigDecimal transactionAmount, Boolean deleted, LocalDateTime createAt) {
        this.id = id;
        this.customerWithdraws = customerWithdraws;
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

    public Customer getCustomerWithdraws() {
        return customerWithdraws;
    }

    public void setCustomerWithdraws(Customer customerWithdraws) {
        this.customerWithdraws = customerWithdraws;
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
