package nl.mca.examples.rest.customer;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class CustomerResource {

    @RequestMapping("/customers")
    public ResponseEntity<List<Customer>> listCustomers() {
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @RequestMapping("/customers/{id}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("id") Long pk) {
        Customer customer = new Customer();
        customer.setPk(pk);

        return new ResponseEntity<>(customer, HttpStatus.OK);
    }

}
