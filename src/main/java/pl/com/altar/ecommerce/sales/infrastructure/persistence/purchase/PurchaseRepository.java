package pl.com.altar.ecommerce.sales.infrastructure.persistence.purchase;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseRepository extends JpaRepository<PurchaseEntity, Long> {


}
