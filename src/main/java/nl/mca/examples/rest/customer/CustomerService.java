package nl.mca.examples.rest.customer;

import nl.mca.examples.rest.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class CustomerService {

    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public Customer create(Customer customer) {
        return this.repository.create(customer);
    }

    public Customer update(Customer customer) {
        find(customer.getPk());
        return this.repository.update(customer);
    }

    public Collection<Customer> list(int offset, int limit) {
        return this.repository.list(offset, limit);
    }

    public Customer find(Long pk) {
        return this.repository.find(pk)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + pk + " does not exist"));
    }

    public void delete(Long pk) {
        this.repository.delete(pk);
    }

}
