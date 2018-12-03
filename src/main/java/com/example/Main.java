package com.example;

import com.example.model.Customer;
import com.example.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        SpringApplication.run(Main.class);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) {
        return args -> {

            CustomerService customerService = new CustomerService(restTemplate);
            ResponseEntity<String> response;
            ResponseEntity<List<Customer>> customerResponse;

            //Post customer List
            List<Customer> customers = new ArrayList<>();
            customers.add(new Customer(50, "Ronnie", "Dallas", "TX", "75240"));
            customers.add(new Customer(51, "Bobby", "Dallas", "TX", "75240"));
            customers.add(new Customer(52, "Ricky", "Dallas", "TX", "75240"));
            customers.add(new Customer(53, "Mike", "Dallas", "TX", "75240"));

            response = customerService.postCustomers(customers);

            log.info("------------------");
            log.info("Post Customer List");
            log.info("------------------");
            log.info("Response: " + response);
            log.info("Response Status: " + response.getStatusCode().toString());
            log.info("Response Body: " + response.getBody());

            // Post Customers
            Customer customer = new Customer(0, "Ronnie", "Dallas", "TX", "75240");

            response = customerService.postCustomers(customers);

            log.info("------------------");
            log.info("Post Customer");
            log.info("------------------");
            log.info("Response: " + response);
            log.info("Response Status: " + response.getStatusCode().toString());
            log.info("Response Body: " + response.getBody());

            //Get Customers
            customerResponse = customerService.getCustomers();

            log.info("------------------");
            log.info("Get Customers");
            log.info("------------------");
            log.info("Response: " + customerResponse);
            log.info("Response Status: " + customerResponse.getStatusCode().toString());

            List<Customer> cl = customerResponse.getBody();

            if (cl != null) {
                for (Customer c : cl) {
                    log.info("Customer: " + c.toString());
                }
            }

            // Get Customer
            response = customerService.getCustomer(2);

            log.info("------------------");
            log.info("Get Customer");
            log.info("------------------");
            log.info("Response: " + response);
            log.info("Response Status: " + response.getStatusCode().toString());
            log.info("Response Body: " + response.getBody());

        };

    }
}
