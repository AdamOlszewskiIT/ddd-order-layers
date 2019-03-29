package pl.com.altar.ecommerce.sales.infrastructure.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


}
