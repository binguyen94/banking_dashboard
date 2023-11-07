package com.example.banking_manage.controller.rest;

import com.example.banking_manage.model.Customer;
import com.example.banking_manage.service.customer.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/customers")

public class CustomerRestController {
    @Autowired
    private ICustomerService customerService;

    @GetMapping
    public List<Customer> findAll() {
        List<Customer> customers = customerService.findCustomersByDeleted(false);
        return customers;
    }

}
