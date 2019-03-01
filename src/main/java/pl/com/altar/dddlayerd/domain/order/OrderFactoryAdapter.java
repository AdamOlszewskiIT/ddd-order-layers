package pl.com.altar.dddlayerd.domain.order;

import lombok.AllArgsConstructor;
import pl.com.altar.dddlayerd.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.client.vm.OrderVM;
import pl.com.altar.dddlayerd.domain.order.events.OrderCreated;
import pl.com.altar.dddlayerd.domain.order.ports.OrderEventPublisher;
import pl.com.altar.dddlayerd.domain.order.ports.OrderFactoryPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderRepositoryPort;

import static pl.com.altar.dddlayerd.domain.order.ModelMapper.map;

@AllArgsConstructor
public class OrderFactoryAdapter implements OrderFactoryPort {
    private static final String ORDER_CREATED = "Order created";
    private final OrderRepositoryPort orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public OrderVM createOrder(CreateOrderCommand createOrderCommand) {
        var newOrder = Order.createOrder(createOrderCommand);
        final var persistedOrder = orderRepository.save(newOrder);
        final var  orderCreated = new OrderCreated(ORDER_CREATED, persistedOrder);
        orderEventPublisher.publish(orderCreated);
        return map(persistedOrder);
    }
}
