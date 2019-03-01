package pl.com.altar.dddlayerd.domain.order;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.client.vm.OrderVM;
import pl.com.altar.dddlayerd.domain.order.ports.OrderQueryPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderRepositoryPort;

import java.util.List;
import java.util.stream.Collectors;

import static pl.com.altar.dddlayerd.domain.order.ModelMapper.mapDetails;

@AllArgsConstructor
public class OrderQueryAdapter implements OrderQueryPort {

    private final OrderRepositoryPort orderRepository;

    @Override
    public OrderDetailsVM findOrder(Long orderId) {
        final var orderOptional = orderRepository.findById(orderId);
        return mapDetails(orderOptional);
    }

    @Override
    public List<OrderVM> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(ModelMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDetailsVM> findAllWithDetails() {
        return orderRepository.findAll()
                .stream()
                .map(ModelMapper::mapDetails)
                .collect(Collectors.toList());
    }

}
