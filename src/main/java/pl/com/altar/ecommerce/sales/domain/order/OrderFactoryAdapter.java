package pl.com.altar.ecommerce.sales.domain.order;

import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.client.command.CreateOrderCommand;
import pl.com.altar.ecommerce.sales.domain.order.events.OrderCreated;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderEventPublisher;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderFactoryPort;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

@AllArgsConstructor
public class OrderFactoryAdapter implements OrderFactoryPort {
    private static final String ORDER_CREATED = "Order created";
    private final OrderRepositoryPort orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public OrderProjection createOrder(CreateOrderCommand createOrderCommand) {
        var newOrder = new Order(createOrderCommand);
        final var persistedOrder = orderRepository.save(newOrder);
        final var  orderCreated = new OrderCreated(ORDER_CREATED, persistedOrder);
        orderEventPublisher.publish(orderCreated);
        return persistedOrder;
    }
}
