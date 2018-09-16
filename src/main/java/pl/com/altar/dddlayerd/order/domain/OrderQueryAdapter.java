package pl.com.altar.dddlayerd.order.domain;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.order.client.exception.OderNotFoundException;
import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;
import pl.com.altar.dddlayerd.order.domain.ports.OrderQueryPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static pl.com.altar.dddlayerd.order.domain.ModelMapper.mapDetails;

@Service
@AllArgsConstructor
class OrderQueryAdapter implements OrderQueryPort {

    private final OrderRepository orderRepository;

    @Override
    public OrderDetailsVM findOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.getOne(orderId);
        return mapDetails(orderOptional.orElseThrow(() -> new OderNotFoundException(orderId)));
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
