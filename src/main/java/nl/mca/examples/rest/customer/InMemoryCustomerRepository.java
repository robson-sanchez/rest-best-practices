package nl.mca.examples.rest.customer;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryCustomerRepository implements CustomerRepository {

    private static final AtomicLong PK_GENERATOR = new AtomicLong();

    private final Map<Long, Customer> customers = new LinkedHashMap<>();

    @Override
    public Customer create(Customer customer) {
        customer.setPk(PK_GENERATOR.incrementAndGet());

        this.customers.put(customer.getPk(), customer);
        return customer;
    }

    @Override
    public Customer update(Customer customer) {
        this.customers.put(customer.getPk(), customer);
        return customer;
    }

    @Override
    public Collection<Customer> list(int offset, int limit) {
        return this.customers.values()
                .stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Customer> find(Long pk) {
        return Optional.ofNullable(this.customers.get(pk));
    }

    @Override
    public void delete(Long pk) {
        this.customers.remove(pk);
    }

}
