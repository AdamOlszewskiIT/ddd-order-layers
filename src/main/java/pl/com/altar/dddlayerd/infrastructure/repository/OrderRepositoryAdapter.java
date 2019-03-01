package pl.com.altar.dddlayerd.infrastructure.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.client.exception.OderNotFoundException;
import pl.com.altar.dddlayerd.domain.order.projections.OrderProjection;
import pl.com.altar.dddlayerd.domain.order.ports.OrderRepositoryPort;

import java.util.ArrayList;
import java.util.List;

import static pl.com.altar.dddlayerd.application.shared.ExceptionUtil.handleOption;

@Service
@AllArgsConstructor
public class OrderRepositoryAdapter implements OrderRepositoryPort {

    private final OrderRepository orderRepository;

    @Override
    public OrderProjection save(OrderProjection order) {
        final var orderEntity = new OrderEntity(order);
        return orderRepository.save(orderEntity);
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
