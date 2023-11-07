package com.example.banking_manage.service.customer;
import com.example.banking_manage.model.Deposit;
import com.example.banking_manage.model.Transfer;
import com.example.banking_manage.model.Withdraw;
import com.example.banking_manage.service.IGeneralService;
import com.example.banking_manage.model.Customer;

import java.lang.reflect.WildcardType;
import java.util.List;

public interface ICustomerService extends IGeneralService<Customer,Long> {
    List<Customer> findCustomersByDeleted(boolean deleted);

    List<Customer> findAllWithoutId(Long id, boolean deleted);

    void deposit(Deposit deposit);

    void withdraw(Withdraw withdraw);

    void transfer(Transfer transfer);


}
