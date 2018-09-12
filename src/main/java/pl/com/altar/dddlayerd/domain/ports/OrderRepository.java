package pl.com.altar.dddlayerd.domain.ports;

import pl.com.altar.dddlayerd.domain.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);

    Optional<Order> getOne(Long id);

    List<Order> findAll();

    void deleteAll();

    Long count();
}
