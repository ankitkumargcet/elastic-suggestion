package com.poc.elastic.service.impl;

import com.poc.elastic.entity.Customer;
import com.poc.elastic.repository.CustomerRepository;
import com.poc.elastic.service.CustomerSearchService;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchPhraseQuery;

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
    public List<String> searchSuggestionByName(String firstname, String lastName, String email) {
        NativeSearchQueryBuilder nativeSearchQueryBuilderQueryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQueryBuilder = boolQuery();
        if (StringUtils.isNotEmpty(firstname))
            boolQueryBuilder.must(matchPhraseQuery("firstname", firstname));
        if (StringUtils.isNotEmpty(lastName))
            boolQueryBuilder.must(matchPhraseQuery("lastName", lastName));
        if (StringUtils.isNotEmpty(email))
            boolQueryBuilder.must(matchPhraseQuery("lastName", email));

        nativeSearchQueryBuilderQueryBuilder.withQuery(boolQueryBuilder);

        SearchHits<Customer> searchHits = elasticsearchRestTemplate.search(nativeSearchQueryBuilderQueryBuilder.build(), Customer.class);
        List<Customer> customers = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());

        return customers.stream()
                .map(customer -> customer.getFirstname() + " " + customer.getLastname())
                .collect(Collectors.toList());
    }

}
