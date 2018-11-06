package nl.mca.examples.rest.customer;

import nl.mca.examples.rest.BaseResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CustomerResource extends BaseResource {

    private final CustomerService customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping(value = "/v1/customers")
    public ResponseEntity<Collection<Customer>> listCustomers(
            @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
            @RequestParam(value = "limit", required = false, defaultValue = "30") int limit,
            @RequestParam(value = "fields", required = false) String fields) {
        Collection<Customer> customers = customerService.list(offset, limit);

        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        String totalRecords = String.valueOf(customers.stream().count());

        ResponseEntity.BodyBuilder builder = ResponseEntity.status(HttpStatus.OK)
                .header("X-Total-Count", totalRecords);

        if (fields != null) {
            List<String> mandatoryFields = Arrays.asList(fields.split(","));

            List<Customer> result = customers
                    .stream()
                    .map(customer -> partialResponse(customer, mandatoryFields))
                    .collect(Collectors.toList());

            return builder.body(result);
        } else {
            return builder.body(customers);
        }

    }

    @PostMapping("/v1/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.create(customer));
    }

    @GetMapping("/v1/customers/{id}")
    public ResponseEntity<Customer> findCustomer(@PathVariable("id") Long pk,
        @RequestParam(value = "fields", required = false) String fields) {
        Customer customer = customerService.find(pk);

        List<String> mandatoryFields = getMandatoryFields(fields);
        return ResponseEntity.ok(partialResponse(customer, mandatoryFields));
    }

    @PutMapping("/v1/customers/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable("id") Long pk, @RequestBody Customer customer) {
        customer.setPk(pk);
        return ResponseEntity.ok(customerService.update(customer));
    }

    @PatchMapping("/v1/customers/{id}")
    public ResponseEntity<Customer> updateCustomerPartially(@PathVariable("id") Long pk, @RequestBody Customer customer) {
        Customer existingCustomer = customerService.find(pk);
        existingCustomer.setPk(pk);

        if (customer.getEmail() != null) {
            existingCustomer.setEmail(customer.getEmail());
        }

        if (customer.getFirstName() != null) {
            existingCustomer.setFirstName(customer.getFirstName());
        }

        if (customer.getLastName() != null) {
            existingCustomer.setLastName(customer.getLastName());
        }

        if (customer.getPhoneNumber() != null) {
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        }

        return ResponseEntity.ok(customerService.update(existingCustomer));
    }

    @DeleteMapping("/v1/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable("id") Long pk) {
        customerService.delete(pk);
        return ResponseEntity.ok().build();
    }

    private List<String> getMandatoryFields(String fields) {
        return fields == null ? Collections.emptyList() : Arrays.asList(fields.split(","));
    }
}
