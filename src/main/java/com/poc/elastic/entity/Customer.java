package com.poc.elastic.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "customer")
public class Customer {

    @Id
    private long id;
    private String firstname;
    private String lastname;
    private String email;
    private LocalDateTime updated;
    private LocalDateTime created;

}
