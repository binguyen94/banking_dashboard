package com.example.banking_manage.controller;

import com.example.banking_manage.model.Deposit;
import com.example.banking_manage.model.Transfer;
import com.example.banking_manage.model.Withdraw;
import com.example.banking_manage.service.customer.CustomerServiceImpl;
import com.example.banking_manage.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.example.banking_manage.model.Customer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customers")

public class CustomerController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public String showListPage(Model model) {
        List<Customer> customers = customerService.findAll();
        model.addAttribute("customers", customers);
        return "customer/list";
    }

    @GetMapping("/create")
    public String showCreatePage(Model model) {
        model.addAttribute("customer", new Customer());

        return "customer/create";
    }

    @GetMapping("/delete/{customerId}")
    public String showCreatePage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);
        model.addAttribute("customer", customer);

        return "customer/delete";
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

    @PostMapping("/delete/{customerId}")
    public String deleteCustomer(@PathVariable Long customerId, RedirectAttributes redirectAttributes) {

        customerService.removeById(customerId);

        redirectAttributes.addFlashAttribute("success", true);
        redirectAttributes.addFlashAttribute("message", "Deleted successfully");

        return "redirect:/customers";
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



    @GetMapping("/update/{customerId}")
    public String showUpdatePage(@PathVariable Long customerId, Model model) {
        Customer customer = customerService.findById(customerId);

        if (customer != null) {
            model.addAttribute("customer", customer);
            return "customer/update";
        } else {
            model.addAttribute("notFoundMessage", "Không tìm thấy khách hàng với ID " + customerId);
            return "customer/update";
        }
    }

    @PostMapping("/update/{customerId}")
    public String updateCustomer(@PathVariable Long customerId, @ModelAttribute Customer updatedCustomer, Model model) {
        try {
            customerService.update(customerId, updatedCustomer);

            model.addAttribute("success", true);
            model.addAttribute("message", "Updated successfully");
        } catch (IllegalArgumentException ex) {
            model.addAttribute("success", false);
            model.addAttribute("message", ex.getMessage());
        }

        return "customer/update";
    }



    @PostMapping("/deposit/{customerId}")
    public String deposit(@PathVariable Long customerId, @ModelAttribute Deposit deposit, Model model) {

        Customer customer = customerService.findById(customerId);

        deposit.setCustomerDeposit(customer);
        customerService.deposit(deposit);

        deposit.setTransactionAmount(null);

        model.addAttribute("deposit", deposit);

        model.addAttribute("success", true);
        model.addAttribute("message", "Deposit successfully");

        return "customer/deposit";
    }

    @GetMapping("/transfer/{senderId}")
    public String showTransferPage(@PathVariable Long senderId, Model model) {

        Customer sender = customerService.findById(senderId);

        List<Customer> recipients = customerService.findAllWithoutId(senderId);

        Transfer transfer = new Transfer();
        transfer.setSender(sender);

        model.addAttribute("transfer", transfer);
        model.addAttribute("recipients", recipients);


        model.addAttribute("success", true);
        model.addAttribute("message", "Transfer successfully");

        return "customer/transfer";
    }



    @PostMapping("/withdraw/{customerId}")
    public String withdraw(@PathVariable Long customerId, @ModelAttribute Withdraw withdraw, Model model) {

        Customer customer = customerService.findById(customerId);
        try {
            withdraw.setCustomerWithdraws(customer);
            customerService.withdraw(withdraw);
        } catch (IllegalArgumentException e) {
            model.addAttribute("success", false);
            model.addAttribute("message", e.getMessage());
            withdraw.setTransactionAmount(null);

            return "customer/withdraw";
        }

        withdraw.setTransactionAmount(null);
        model.addAttribute("withdraw", withdraw);

        model.addAttribute("success", true);
        model.addAttribute("message", "Withdraw successfully");

        return "customer/withdraw";
    }

}
