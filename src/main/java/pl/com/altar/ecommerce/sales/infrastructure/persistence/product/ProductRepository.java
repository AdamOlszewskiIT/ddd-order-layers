package pl.com.altar.ecommerce.sales.infrastructure.persistence.product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
}
