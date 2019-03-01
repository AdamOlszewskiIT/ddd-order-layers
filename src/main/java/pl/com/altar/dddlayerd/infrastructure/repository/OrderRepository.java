package pl.com.altar.dddlayerd.infrastructure.repository;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends Repository<OrderEntity, Long> {

    OrderEntity save(OrderEntity order);

    Optional<OrderEntity> findById(Long id);

    List<OrderEntity> findAll();

    void deleteAll();

    Long count();

}
