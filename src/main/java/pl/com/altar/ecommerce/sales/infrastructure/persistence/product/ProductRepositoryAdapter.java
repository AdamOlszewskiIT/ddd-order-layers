package pl.com.altar.ecommerce.sales.infrastructure.persistence.product;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.com.altar.ecommerce.sales.client.exception.ResourceNotFoundException;
import pl.com.altar.ecommerce.sales.domain.product.ports.ProductRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;

import static pl.com.altar.ecommerce.shared.ExceptionUtil.handleOption;

@Repository
@AllArgsConstructor
public class ProductRepositoryAdapter implements ProductRepositoryPort {

    private final ProductRepository repository;

    @Override
    public ProductData save(ProductData product) {
        return repository.save(new ProductEntity(product));
    }

    @Override
    public ProductData findById(Long productId) {
        return handleOption(repository.findById(productId), new ResourceNotFoundException(this.getClass().getName(), productId));
    }
}
