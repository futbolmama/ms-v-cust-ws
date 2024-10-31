package dev.futbolmama.msvcustws.controller;

import com.github.javafaker.Faker;
import dev.futbolmama.msvcustws.AbstractIntegrationTest;
import dev.futbolmama.msvcustws.db.model.Customer;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@Sql(scripts = "classpath:/create-table-seed.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "classpath:/drop-tables.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class CustomerControllerTest extends AbstractIntegrationTest {
    private final Faker faker = Faker.instance(Locale.US, ThreadLocalRandom.current());

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    @DisplayName("Test all the endpoints")
    void testAllEndpoints() {

        // get all customers
        ResponseEntity<List> listCustResponse = restTemplate.getForEntity("/v1/api/customer/all", List.class);
        assertEquals(HttpStatus.OK, listCustResponse.getStatusCode());
        assertFalse(listCustResponse.getBody().isEmpty());

        // update a customer
        var updateCustReq = new UpdateCustomerRequest(
                1L,
                faker.lorem().characters(5),
                faker.lorem().characters(5),
                faker.lorem().characters(5),
                faker.internet().emailAddress(),
                faker.phoneNumber().phoneNumber());
        ResponseEntity<Void> updateCustResp = restTemplate.exchange(
                "/v1/api/customer/1",
                HttpMethod.PUT,
                new HttpEntity<>(updateCustReq),
                Void.class
        );
        assertEquals(HttpStatus.OK, updateCustResp.getStatusCode());

        // get 1 customer
        ResponseEntity<Customer> singleCustResponse = restTemplate.getForEntity("/v1/api/customer/1", Customer.class);
        assertEquals(HttpStatus.OK, singleCustResponse.getStatusCode());
        assertFalse(singleCustResponse.getBody().mName().isEmpty());

        // create a customer
        var createCustReq = new CreateCustomerRequest(
                faker.lorem().characters(5),
                null,
                faker.lorem().characters(5),
                faker.internet().emailAddress(),
                faker.phoneNumber().phoneNumber());

        ResponseEntity<?> createCustResp = restTemplate.postForEntity("/v1/api/customer", createCustReq, Customer.class);
        assertEquals(HttpStatus.CREATED, createCustResp.getStatusCode());

        // delete a customer
        ResponseEntity<Void> deleteCustResp = restTemplate.exchange(
                "/v1/api/customer/1",
                HttpMethod.DELETE,
                null,
                Void.class
        );
        assertEquals(HttpStatus.OK, deleteCustResp.getStatusCode());

    }

}