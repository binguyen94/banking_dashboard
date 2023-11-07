package com.example.banking_manage.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="transfers")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "id", nullable = false)
    private Customer sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", referencedColumnName = "id", nullable = false)
    private Customer recipient;

    @Column(nullable = false)
    private BigDecimal transferAmount;
    @Column(nullable = false)
    private long fees = 10;

    @Column(nullable = false)
    private BigDecimal feesAmount;

    @Column(nullable = false)
    private BigDecimal transactionAmount;
    @Column(nullable = false, columnDefinition = "TINYINT(1)")
    private Boolean deleted = false;

    private LocalDateTime createAt;

    public Transfer() {
    }

    public Transfer(Long id, Customer sender, Customer recipient, BigDecimal transferAmount, long fees, BigDecimal feesAmount, BigDecimal transactionAmount, Boolean deleted, LocalDateTime createAt) {
        this.id = id;
        this.sender = sender;
        this.recipient = recipient;
        this.transferAmount = transferAmount;
        this.fees = fees;
        this.feesAmount = feesAmount;
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

    public Customer getSender() {
        return sender;
    }

    public void setSender(Customer sender) {
        this.sender = sender;
    }

    public Customer getRecipient() {
        return recipient;
    }

    public void setRecipient(Customer recipient) {
        this.recipient = recipient;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public long getFees() {
        return fees;
    }

    public void setFees(long fees) {
        this.fees = fees;
    }

    public BigDecimal getFeesAmount() {
        return feesAmount;
    }

    public void setFeesAmount(BigDecimal feesAmount) {
        this.feesAmount = feesAmount;
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
