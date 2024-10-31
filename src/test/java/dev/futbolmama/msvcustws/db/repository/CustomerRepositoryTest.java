package dev.futbolmama.msvcustws.db.repository;

import com.github.javafaker.Faker;
import dev.futbolmama.msvcustws.AbstractIntegrationTest;
import dev.futbolmama.msvcustws.db.model.Customer;
import java.time.Instant;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Sql("classpath:/create-table-seed.sql")
class CustomerRepositoryTest extends AbstractIntegrationTest {
    private final Faker faker = Faker.instance(Locale.US, ThreadLocalRandom.current());

    @Autowired
    JdbcClient jdbcClient;
    private CustomerRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository = new CustomerRepository(jdbcClient);
    }

    @Test
    @DisplayName("Test all persistence operations")
    void saveAll() {
        var customer1 = new Customer(
                null,
                faker.lorem().characters(5),
                null,
                faker.lorem().characters(5),
                faker.internet().emailAddress(),
                faker.phoneNumber().phoneNumber(),
                null
        );

        var custId = customerRepository.save(customer1);
        customerRepository.findById(custId).ifPresent(cust -> {
            assertEquals(custId, cust.id());
        });

        var customer2 = new Customer(
                custId,
                faker.lorem().characters(5),
                null,
                faker.lorem().characters(5),
                faker.internet().emailAddress(),
                faker.phoneNumber().phoneNumber(),
                Instant.now()
        );

        customerRepository.update(customer2);
        customerRepository.findById(custId).ifPresent(cust -> {
            assertNotEquals(customer1.email(), cust.email());
        });

        customerRepository.deleteById(custId);
        assertTrue(customerRepository.findById(custId).isEmpty());
    }

}