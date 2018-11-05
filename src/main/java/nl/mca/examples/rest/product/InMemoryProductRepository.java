package nl.mca.examples.rest.product;

import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InMemoryProductRepository implements ProductRepository {

    private static final AtomicLong PK_GENERATOR = new AtomicLong();

    private final Map<String, Product> products = new LinkedHashMap<>();

    @Override
    public Product create(Product product) {
        product.setPk(PK_GENERATOR.incrementAndGet());

        this.products.put(product.getCode(), product);
        return product;
    }

    @Override
    public Product update(Product product) {
        this.products.put(product.getCode(), product);
        return product;
    }

    @Override
    public Collection<Product> list(int offset, int limit) {
        return this.products.values()
                .stream()
                .skip(offset)
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> find(String code) {
        return Optional.ofNullable(this.products.get(code));
    }

    @Override
    public void delete(String code) {
        this.products.remove(code);
    }

}
