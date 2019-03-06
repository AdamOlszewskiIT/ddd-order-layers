package pl.com.altar.ecommerce.sales.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {


}
