package pl.com.altar.ecommerce.sales.domain.product.ports;

import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainRepository;

@DomainRepository
public interface ProductRepositoryPort {
    ProductData save(ProductData product);
    ProductData findById(Long productId);
}
