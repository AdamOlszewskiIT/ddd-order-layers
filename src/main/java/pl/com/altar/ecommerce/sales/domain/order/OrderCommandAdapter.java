package pl.com.altar.ecommerce.sales.domain.order;


import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.exception.OderItemNotFoundException;
import pl.com.altar.ecommerce.sales.domain.order.events.ItemAdded;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderCommandPort;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderEventPublisher;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

import java.util.Objects;

@AllArgsConstructor
@Transactional
public class OrderCommandAdapter implements OrderCommandPort {

    private static final String ITEM_ADDED = "Item added";
    private final OrderRepositoryPort orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public OrderProjection addItem(Long orderId, AddItemCommand addItemCommand) {
        final Order foundedOrder = findOrderById(orderId);
        foundedOrder.addItem(addItemCommand);
        final var persistedOrder = orderRepository.save(foundedOrder);
        orderEventPublisher.publish(new ItemAdded(ITEM_ADDED, persistedOrder));
        return persistedOrder;
    }

    @Override
    public void deleteOrder(Long orderId) {
        final Order foundedOrder = findOrderById(orderId);
        foundedOrder.archive();
        orderRepository.save(foundedOrder);
    }


    @Override
    public void deleteOrderItem(Long orderId, Long itemId) {
        final Order foundedOrder = findOrderById(orderId);
        foundedOrder.removeOrderItem(itemId);
        orderRepository.save(foundedOrder);
    }

    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    private Order findOrderById(Long orderId) {
        final var foundedOrderProjection = orderRepository.findById(orderId);
        return new Order(foundedOrderProjection);
    }
}
