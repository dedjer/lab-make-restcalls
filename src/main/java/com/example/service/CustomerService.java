package com.example.service;

import com.example.Main;
import com.example.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class CustomerService {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private RestTemplate restTemplate;
    private final String url = "http://5c0531cd6b84ee00137d2541.mockapi.io/api/customer";

    public CustomerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // POST LIST
    public ResponseEntity<String> postCustomers(List<Customer> customers) {
        return restTemplate.postForEntity(url, customers, String.class);
    }

    // POST SINGLE
    public ResponseEntity<String> postCustomer(Customer customer) {
        return restTemplate.postForEntity(url, customer, String.class);
    }

    // GET ALL
    public ResponseEntity<List<Customer>> getCustomers() {
        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Customer>>() {});
    }

    // GET SINGLE
    public ResponseEntity<String> getCustomer(int id) {
        return restTemplate.getForEntity(url + "/" + id, String.class);
    }

    // TODO: PUT

    // TODO: PATCH

    // TODO: DELETE

}
