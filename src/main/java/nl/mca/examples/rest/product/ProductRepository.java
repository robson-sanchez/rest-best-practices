package nl.mca.examples.rest.product;

import java.util.Collection;
import java.util.Optional;

public interface ProductRepository {

    Product create(Product product);

    Product update(Product product);

    Collection<Product> list(int offset, int limit);

    Optional<Product> find(String code);

    void delete(String code);

}
