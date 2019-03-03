package pl.com.altar.ecommerce.sales.infrastructure.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.altar.ecommerce.sales.client.exception.OderNotFoundException;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderRepositoryPort;

import java.util.ArrayList;
import java.util.List;

import static pl.com.altar.ecommerce.shared.ExceptionUtil.handleOption;

@Service
@AllArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderRepository orderRepository;

    @Override
    public OrderProjection save(OrderProjection order) {
        final var orderEntity = new OrderEntity(order);
        return orderRepository.saveAndFlush(orderEntity);
    }

    @Override
    public OrderProjection findById(Long id) {
        return handleOption(orderRepository.findById(id), new OderNotFoundException(id));
    }

    @Override
    public List<OrderProjection> findAll() {
        return new ArrayList<>(orderRepository.findAll());
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    @Override
    public Long count() {
        return orderRepository.count();
    }
}
