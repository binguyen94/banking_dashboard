package com.example.banking_manage.service.customer;

import com.example.banking_manage.model.Customer;
import com.example.banking_manage.model.Deposit;
import com.example.banking_manage.model.Transfer;
import com.example.banking_manage.model.Withdraw;
import com.example.banking_manage.repository.ICustomerRepository;
import com.example.banking_manage.service.transfer.TransferServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private final ICustomerRepository iCustomerRepository;
//    private static final List<Customer> customers = new ArrayList<>();
//    private static long id = 1L;
//
//    static {
//        customers.add(new Customer(id++, "NVA", "nva@co.cc", "2345", "28 Nguyễn Tri Phương", BigDecimal.ZERO, false));
//
//    }

//    public CustomerServiceImpl() {
//    }

    @Override
    public List<Customer> findAll() {
        return iCustomerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return iCustomerRepository.findById(id).get();
    }

    //    @Override
//    public List<Customer> findAllWithoutId(Long id) {
//        return customers.stream().filter(customer -> !Objects.equals(customer.getId(), id)).collect(Collectors.toList());
//    }
    @Override
    public List<Customer> findAllWithoutId(Long id) {
        return iCustomerRepository.findAll().stream().filter(customer -> !customer.getId().equals(id)).collect(Collectors.toList());
    }


    @Override
    public void create(Customer customer) {
        customer.setBalance(BigDecimal.ZERO);
        customer.setDeleted(false);

        iCustomerRepository.save(customer);
    }

    @Override
    public void update(Long customerId, Customer updatedCustomer) {
        Optional<Customer> optionalCustomer = iCustomerRepository.findById(customerId);

        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();

            customer.setFullName(updatedCustomer.getFullName());
            customer.setEmail(updatedCustomer.getEmail());
            customer.setPhone(updatedCustomer.getPhone());
            customer.setAddress(updatedCustomer.getAddress());

            iCustomerRepository.save(customer);
        } else {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với id " + customerId);
        }
    }


    @Override
    public void deposit(Deposit deposit) {
        Customer customer = deposit.getCustomerDeposit();
        BigDecimal transactionAmount = deposit.getTransactionAmount();

        Optional<Customer> optionalCustomer = iCustomerRepository.findById(customer.getId());

        if (optionalCustomer.isPresent()) {
            Customer managedCustomer = optionalCustomer.get();

            managedCustomer.setBalance(managedCustomer.getBalance().add(transactionAmount));

            iCustomerRepository.save(managedCustomer);
        } else {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với id " + customer.getId());
        }
    }


    @Override
    public void withdraw(Withdraw withdraw) {
        Customer customer = withdraw.getCustomerWithdraws();
        BigDecimal transactionAmount = withdraw.getTransactionAmount();

        Optional<Customer> optionalCustomer = iCustomerRepository.findById(customer.getId());

        if (optionalCustomer.isPresent()) {
            Customer managedCustomer = optionalCustomer.get();

            // Check if the customer has enough money to withdraw the requested amount.
            if (managedCustomer.getBalance().compareTo(transactionAmount) < 0) {
                throw new IllegalArgumentException("Don't have enough money to withdraw this amount..");
            }

            // Update the customer's balance.
            managedCustomer.setBalance(managedCustomer.getBalance().subtract(transactionAmount));

            // Save the customer object to the database.
            iCustomerRepository.save(managedCustomer);
        } else {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với id " + customer.getId());
        }
    }


    @Override
    public void transfer(Transfer transfer) {


    }


    @Override
    public void removeById(Long id) {
        Optional<Customer> optionalCustomer = iCustomerRepository.findById(id);

        if (optionalCustomer.isPresent()) {
            iCustomerRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Không tìm thấy khách hàng với id " + id);
        }
    }

}

