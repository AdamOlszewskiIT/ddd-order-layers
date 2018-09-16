package pl.com.altar.dddlayerd.order.domain;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
interface OrderRepository extends Repository<Order, Long> {

    Order save(Order order);

    Optional<Order> findById(Long id);

    List<Order> findAll();

    void deleteAll();

    Long count();

}
