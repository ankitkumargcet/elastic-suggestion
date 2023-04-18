package com.poc.elastic.service;

import com.poc.elastic.entity.Customer;

import java.util.List;

public interface CustomerSearchService {

    Iterable<Customer> getAllCustomers();

    Iterable<Customer> getCustomerByEmail(String email);

    Iterable<Customer> getCustomerByFirstname(String firstName);

    List<String> searchSuggestionByFirstname(String firstname);

    List<String> searchSuggestionByName(String name);

}
