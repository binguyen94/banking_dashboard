package com.example.banking_manage.service.customer;

import com.example.banking_manage.model.Customer;
import com.example.banking_manage.model.Deposit;
import com.example.banking_manage.model.Transfer;
import com.example.banking_manage.model.Withdraw;
import com.example.banking_manage.repository.ICustomerRepository;
import com.example.banking_manage.repository.IDepositRepository;
import com.example.banking_manage.repository.ITransferRepository;
import com.example.banking_manage.repository.IWithdrawRepository;
import com.example.banking_manage.service.transfer.TransferServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    private IDepositRepository depositRepository;

    @Autowired
    private IWithdrawRepository withdrawRepository;

    @Autowired
    private ITransferRepository transferRepository;
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
    public List<Customer> findCustomersByDeleted(boolean deleted) {
        return customerRepository.findCustomersByDeleted(deleted);
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id).get();
    }


    //    @Override
//    public List<Customer> findAllWithoutId(Long id) {
//        return customers.stream().filter(customer -> !Objects.equals(customer.getId(), id)).collect(Collectors.toList());
//    }
    @Override
    public List<Customer> findAllWithoutId(Long id, boolean deleted) {
        List<Customer> allCustomers = customerRepository.findCustomersByDeleted(deleted);
        List<Customer> customers = allCustomers.stream()
                .filter(customer -> !customer.getId().equals(id))
                .collect(Collectors.toList());
        return customers;
    }



    @Override
    public void create(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void update(Long id, Customer customer) {
        customer.setId(id);
        customerRepository.save(customer);
    }


    @Override
    public void removeById(Long id) {
        Optional<Customer> existingCustomer = Optional.ofNullable(findById(id));
        Customer customer = existingCustomer.get();
        customer.setDeleted(true);
        customerRepository.save(customer);
    }


    @Override
    public void deposit(Deposit deposit) {
        deposit.setCreateAt(LocalDateTime.now());



//        Customer customer = deposit.getCustomer();
//        BigDecimal currentBalance = customer.getBalance();
//        BigDecimal transactionAmount = deposit.getTransactionAmount();
//        BigDecimal newBalance = currentBalance.add(transactionAmount);
//        customer.setBalance(newBalance);
//        customerRepository.save(customer);

        depositRepository.incrementBalance(deposit.getCustomerDeposit().getId(), deposit.getTransactionAmount());
        depositRepository.save(deposit);
    }


    @Override
    public void withdraw(Withdraw withdraw) {
        withdraw.setCreateAt(LocalDateTime.now());


//        Customer customer = withdraw.getCustomerWithdraws();
//        BigDecimal currentBalance = customer.getBalance();
//        BigDecimal transactionAmount = withdraw.getTransactionAmount();
//        BigDecimal newBalance = currentBalance.subtract(transactionAmount);
//        customer.setBalance(newBalance);
//
//        customerRepository.save(customer);

        withdrawRepository.decrementBalance(withdraw.getCustomerWithdraws().getId(), withdraw.getTransactionAmount());
        withdrawRepository.save(withdraw);
    }


    @Override
    public void transfer(Transfer transfer) {
        BigDecimal senderBalance = transfer.getSender().getBalance();
        BigDecimal transferAmount = transfer.getTransferAmount();

        transfer.setFeesAmount(transfer.getTransactionAmount().subtract(transfer.getTransferAmount()));
        BigDecimal feesAmount = transfer.getFeesAmount();

        Customer sender = transfer.getSender();
        Customer recipient = transfer.getRecipient();

        BigDecimal newSenderBalance = senderBalance.subtract(transferAmount).subtract(feesAmount);
        sender.setBalance(newSenderBalance);

        BigDecimal newRecipientBalance = transfer.getRecipient().getBalance().add(transferAmount);
        recipient.setBalance(newRecipientBalance);

        transfer.setFeesAmount(feesAmount);
        transfer.setTransactionAmount(transferAmount);

        customerRepository.save(sender);
        customerRepository.save(recipient);

        transfer.setCreateAt(LocalDateTime.now());

        transferRepository.save(transfer);
    }




}

