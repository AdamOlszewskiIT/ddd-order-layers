package pl.com.altar.dddlayerd.domain.ports;

import pl.com.altar.dddlayerd.domain.Order;

import java.util.Optional;

public interface OrderQueryPort {

    Optional<Order> findOrder(Long orderId);

}
