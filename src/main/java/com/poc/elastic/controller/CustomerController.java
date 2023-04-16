package com.poc.elastic.controller;

import com.poc.elastic.entity.Customer;
import com.poc.elastic.service.CustomerSearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<String> suggestCustomers(@RequestParam String query) {
        return customerSearchService.searchSuggestionByFirstname(query);
    }

}
