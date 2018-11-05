package nl.mca.examples.rest.customer;

import java.util.Collection;
import java.util.Optional;

public interface CustomerRepository {

    Customer create(Customer Customer);

    Customer update(Customer Customer);

    Collection<Customer> list(int offset, int limit);

    Optional<Customer> find(Long pk);

    void delete(Long pk);
    
}
