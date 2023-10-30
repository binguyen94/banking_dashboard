package com.example.banking_manage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name="customers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String address;
    private BigDecimal balance;
    private Boolean deleted;
    @OneToMany(mappedBy = "customerDeposit")
    private List<Deposit> deposits;
    @OneToMany(mappedBy = "customerWithdraws")
    private List<Withdraw> withdraws;



}
