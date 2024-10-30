package dev.futbolmama.msvcustws.controller;

import dev.futbolmama.msvcustws.db.model.Customer;
import dev.futbolmama.msvcustws.db.repository.CustomerRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api/customer")
public class CustomerController {
    private final CustomerRepository customerRepository;

    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("/all")
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return customerRepository.findById(id)
                                 .map(ResponseEntity::ok)
                                 .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void saveCustomer(@RequestBody CreateCustomerRequest customer) {
        var c = new Customer(null, customer.fName(), customer.mName(), customer.lName(), customer.email(), customer.phone(), null);
        customerRepository.save(c);

    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable Long id, @RequestBody UpdateCustomerRequest customer) {
        var orig = customerRepository.findById(id);
        if (orig.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var update = new Customer(customer.id(), customer.fName(), customer.mName(), customer.lName(), customer.email(), customer.phone(), Instant.now());
        customerRepository.update(update);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerRepository.deleteById(id);
    }
}
