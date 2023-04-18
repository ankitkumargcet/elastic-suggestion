package com.poc.elastic.controller;

import com.poc.elastic.entity.Customer;
import com.poc.elastic.service.CustomerSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerSearchService customerSearchService;

    @GetMapping("/findAll")
    public Iterable<Customer> getCustomers() {
        return customerSearchService.getAllCustomers();
    }

    @GetMapping("/findByEmail/{email}")
    public Iterable<Customer> getCustomerByEmail(@PathVariable("email") String email) {
        return customerSearchService.getCustomerByEmail(email);
    }

    @GetMapping("/findByFirstname/{firstname}")
    public Iterable<Customer> getCustomerByFirstname(@PathVariable("firstname") String firstname) {
        return customerSearchService.getCustomerByFirstname(firstname);
    }

    @GetMapping("/suggestions")
    public List<String> suggestCustomers(@RequestParam String name) {
        return customerSearchService.searchSuggestionByName(name);
    }

}
