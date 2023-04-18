package com.poc.elastic.service.impl;

import com.poc.elastic.entity.Customer;
import com.poc.elastic.repository.CustomerRepository;
import com.poc.elastic.service.CustomerSearchService;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerSearchServiceImpl implements CustomerSearchService {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public Iterable<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public Iterable<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    @Override
    public Iterable<Customer> getCustomerByFirstname(String firstName) {
        return customerRepository.findByFirstname(firstName);
    }

    @Override
    public List<String> searchSuggestionByFirstname(String firstname) {
        QueryBuilder queryBuilder = QueryBuilders.matchPhrasePrefixQuery("firstname", firstname);
        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();
        SearchHits<Customer> searchHits = elasticsearchRestTemplate.search(searchQuery, Customer.class);
        List<Customer> customers = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        return customers.stream()
                .map(customer -> customer.getFirstname() + " " + customer.getLastname())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> searchSuggestionByName(String name) {
        QueryBuilder queryBuilder;

        if (name.contains(" ")) {
            String[] names = name.split(" ");
            queryBuilder = QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchPhrasePrefixQuery("firstname", names[0]))
                    .should(QueryBuilders.matchPhrasePrefixQuery("lastname", names[1]));
        } else {
            queryBuilder = QueryBuilders.boolQuery()
                    .should(QueryBuilders.matchPhrasePrefixQuery("firstname", name))
                    .should(QueryBuilders.matchPhrasePrefixQuery("lastname", name));
        }

        Query searchQuery = new NativeSearchQueryBuilder()
                .withQuery(queryBuilder)
                .build();

        SearchHits<Customer> searchHits = elasticsearchRestTemplate.search(searchQuery, Customer.class);
        List<Customer> customers = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        return customers.stream()
                .map(customer -> customer.getFirstname() + " " + customer.getLastname())
                .collect(Collectors.toList());
    }

}
