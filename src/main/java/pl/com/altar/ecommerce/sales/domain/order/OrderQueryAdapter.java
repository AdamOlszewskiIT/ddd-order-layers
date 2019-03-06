package pl.com.altar.ecommerce.sales.domain.order;


import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderQueryPort;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

import java.util.List;


@AllArgsConstructor
public class OrderQueryAdapter implements OrderQueryPort {

    private final OrderRepositoryPort orderRepository;

    @Override
    public OrderProjection findOrder(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<OrderProjection> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<OrderProjection> findAllWithDetails() {
        return orderRepository.findAll();
    }

}
