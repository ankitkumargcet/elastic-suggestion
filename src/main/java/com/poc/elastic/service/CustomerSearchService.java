package com.poc.elastic.service;

import com.poc.elastic.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.List;

public interface CustomerSearchService {

    Iterable<Customer> getAllCustomers();

    Iterable<Customer> getCustomerByEmail(String email);

    Iterable<Customer> getCustomerByFirstname(String firstName);

    List<String> searchSuggestionByFirstname(String firstname);

    List<String> searchSuggestionByName(String firstname, String lastname, String email);

}
