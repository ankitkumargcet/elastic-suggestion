package com.poc.elastic.repository;

import com.poc.elastic.entity.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface CustomerRepository extends ElasticsearchRepository<Customer, Long> {

    List<Customer> findByFirstname(String firstName);
    List<Customer> findByEmail(String email);

}