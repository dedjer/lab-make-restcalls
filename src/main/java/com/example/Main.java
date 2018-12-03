package com.example;

import com.example.model.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.xml.ws.Response;
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

            // https://www.mockapi.io

            ///////////////////////////////
            //POST LIST
            ///////////////////////////////
            List<Customer> postCustomerList = new ArrayList<>();
            postCustomerList.add(new Customer(50, "Ronnie", "Dallas", "TX", "75240"));
            postCustomerList.add(new Customer(51, "Bobby", "Dallas", "TX", "75240"));
            postCustomerList.add(new Customer(52, "Ricky", "Dallas", "TX", "75240"));
            postCustomerList.add(new Customer(53, "Mike", "Dallas", "TX", "75240"));

            ResponseEntity<String> responseEntityList = restTemplate.postForEntity(
                    "http://5c0531cd6b84ee00137d2541.mockapi.io/api/customer",
                    postCustomerList,
                    String.class);
            HttpStatus statusEntityList = responseEntityList.getStatusCode();
            String responseCustomerList = responseEntityList.getBody();

            log.info("Response: " + responseEntityList);
            log.info("Status: " + statusEntityList);
            log.info("Customer: " + responseCustomerList);

            ///////////////////////////////
            //POST SINGLE NO STATUS CODE
            ///////////////////////////////
            Customer postCustomer1 = new Customer(0, "Ronnie", "Dallas", "TX", "75240");

            String responseCustomer1 = restTemplate.postForObject(
                    "http://5c0531cd6b84ee00137d2541.mockapi.io/api/customer",
                    postCustomer1,
                    String.class);

            log.info(responseCustomer1);

            ///////////////////////////////
            //POST SINGLE WITH STATUS CODE
            ///////////////////////////////
            Customer postCustomer2 = new Customer(0, "Bobby", "Dallas", "TX", "75240");

            ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://5c0531cd6b84ee00137d2541.mockapi.io/api/customer", postCustomer2, String.class);
            HttpStatus status = responseEntity.getStatusCode();
            String responseCustomer2 = responseEntity.getBody();

            log.info("Response: " + responseEntity);
            log.info("Status: " + status);
            log.info("Customer: " + responseCustomer2);

            ///////////////////////////////
            //GET ALL
            ///////////////////////////////
            ResponseEntity<List<Customer>> response = restTemplate.exchange(
                    "http://5c0531cd6b84ee00137d2541.mockapi.io/api/customer",
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Customer>>() {});
            List<Customer> customers = response.getBody();

            if (customers != null) {
                for (Customer c: customers) {
                    log.info(c.toString());
                }
            }

            ///////////////////////////////
            //GET BY ID
            ///////////////////////////////
            Customer customer = restTemplate.getForObject("http://5c0531cd6b84ee00137d2541.mockapi.io/api/customer/2", Customer.class);

            if (customer != null) {
                log.info(customer.toString());
            }


            //PUT


            //PATCH
        };

    }
}
