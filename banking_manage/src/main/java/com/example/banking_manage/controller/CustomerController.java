package com.example.banking_manage.controller;

import com.example.banking_manage.model.Deposit;
import com.example.banking_manage.model.Transfer;
import com.example.banking_manage.model.Withdraw;
import com.example.banking_manage.service.customer.ICustomerService;
import com.example.banking_manage.service.deposit.IDepositService;
import com.example.banking_manage.service.transfer.ITransferService;
import com.example.banking_manage.service.withdraw.IWithdrawService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.banking_manage.model.Customer;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/customers")

public class CustomerController {
    @Autowired
    private ICustomerService customerService;
    @Autowired
    private ITransferService transferService;
    @Autowired
    private IDepositService depositService;
    @Autowired
    private IWithdrawService withdrawService;

    @GetMapping
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findCustomersByDeleted(false);
        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @GetMapping("/edit/{customerId}")
    public String showUpdatePage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "customer/edit";
    }

    @GetMapping("/delete/{customerId}")
    public String showDeletePage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);
        return "customer/delete";
    }

    @GetMapping("/deposit/{customerId}")
    public String showDepositPage(@PathVariable Long customerId, Model model) {

        Customer customer = customerService.findById(customerId);
        Deposit deposit = new Deposit();
        deposit.setCustomerDeposit(customer);

        model.addAttribute("deposit", deposit);

        return "customer/deposit";
    }

    @GetMapping("/withdraw/{customerId}")
    public String showWithdrawPage(@PathVariable Long customerId, Model model) {

        Customer customer = customerService.findById(customerId);
        Withdraw withdraw = new Withdraw();
        withdraw.setCustomerWithdraws(customer);

        model.addAttribute("withdraw", withdraw);

        return "customer/withdraw";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {

        Customer sender = customerService.findById(senderId);

        List<Customer> recipients = customerService.findAllWithoutId(senderId,false);
//
        Transfer transfer = new Transfer();
        transfer.setSender(sender);
//
        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);

        return "customer/transfer";
    }
    @PostMapping("/create")
    public String createCustomer(@ModelAttribute Customer customer, Model model) {

        if (customer.getFullName().length() == 0) {
            model.addAttribute("success", false);
            model.addAttribute("message", "Created unsuccessful");
        }
        else {
            customerService.create(customer);

            model.addAttribute("customer", new Customer());

            model.addAttribute("success", true);
            model.addAttribute("message", "Created successfully");
        }

        return "customer/create";
    }

    @PostMapping("/edit/{customerId}")
    public String updateCustomer(@PathVariable Long customerId, @ModelAttribute Customer customer, Model model) {

        customerService.update(customerId,customer);

        model.addAttribute("success", true);
        model.addAttribute("message", "Updated successful");

        model.addAttribute("customer", customer);

        return "customer/edit";
    }

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, RedirectAttributes redirectAttributes) {

        customerService.removeById(customerId);

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");

        return "redirect:/customers/";
    }

    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {

        Customer customer = customerService.findById(customerId);
//
        deposit.setCustomerDeposit(customer);
        customerService.deposit(deposit);

        customer = customerService.findById(customerId);

        Deposit newDeposit = new Deposit();
        newDeposit.setCustomerDeposit(customer);
//
//        deposit.setTransactionAmount(null);
//
        model.addAttribute("deposit", newDeposit);
//
        model.addAttribute("success", true);
        model.addAttribute("message", "Deposit successfully");

        return "customer/deposit";
    }

    @PostMapping("/withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model) {

        Customer customer = customerService.findById(customerId);
//
        withdraw.setCustomerWithdraws(customer);
        customerService.withdraw(withdraw);

        customer = customerService.findById(customerId);

        Withdraw newWithdraw = new Withdraw();
        newWithdraw.setCustomerWithdraws(customer);
//
//        deposit.setTransactionAmount(null);
//
        model.addAttribute("withdraw", newWithdraw);
//
        model.addAttribute("success", true);
        model.addAttribute("message", "Withdraw successfully");

        return "customer/withdraw";
    }

    @PostMapping("/transfer/{senderId}")
    public String transfer(@PathVariable Long senderId, @RequestParam Long recipientId, @ModelAttribute Transfer transfer, Model model, @RequestParam BigDecimal transactionAmount) {

        Customer sender = customerService.findById(senderId);
        Customer recipient = customerService.findById(recipientId);

        transfer.setSender(sender);
        transfer.setRecipient(recipient);

        if (customerService.findById(senderId).getBalance().compareTo(transactionAmount) >= 0 && transactionAmount.compareTo(BigDecimal.ZERO) > 0 && !transfer.getSender().getDeleted()) {
            transfer.setTransactionAmount(transactionAmount);
            customerService.transfer(transfer);

            sender = customerService.findById(senderId);
            recipient = customerService.findById(recipientId);

            transfer.setSender(sender);
            transfer.setRecipient(recipient);

            model.addAttribute("success", true);
            model.addAttribute("message", "Transfer successfully");
        } else {
            transfer.setSender(sender);
            transfer.setRecipient(recipient);

            model.addAttribute("success", false);
            model.addAttribute("message", "Transfer unsuccessfully");
        }

        List<Customer> recipients = customerService.findAllWithoutId(senderId, false);
        model.addAttribute("recipients", recipients);
        return "customer/transfer";
    }

}
