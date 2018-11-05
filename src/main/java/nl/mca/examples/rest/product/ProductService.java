package nl.mca.examples.rest.product;

import nl.mca.examples.rest.exception.ResourceAlreadyExistsException;
import nl.mca.examples.rest.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public Product create(Product product) {
        String code = product.getCode();

        if (this.repository.find(code).isPresent()) {
            throw new ResourceAlreadyExistsException("Product " + code + " already exists");
        }

        return this.repository.create(product);
    }

    public Product update(Product product) {
        Product productDB = find(product.getCode());

        product.setPk(productDB.getPk());

        return this.repository.update(product);
    }

    public Collection<Product> list(int offset, int limit) {
        return this.repository.list(offset, limit);
    }

    public Product find(String code) {
        return this.repository.find(code)
                .orElseThrow(() -> new ResourceNotFoundException("Product " + code + " does not exist"));
    }

    public void delete(String code) {
        this.repository.delete(code);
    }

}
