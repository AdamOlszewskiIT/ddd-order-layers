package pl.com.altar.dddlayerd.domain.order;


import lombok.AllArgsConstructor;
import pl.com.altar.dddlayerd.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.client.exception.OderItemNotFoundException;
import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.domain.order.events.ItemAdded;
import pl.com.altar.dddlayerd.domain.order.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderEventPublisher;
import pl.com.altar.dddlayerd.domain.order.ports.OrderRepositoryPort;

import java.util.Objects;

import static pl.com.altar.dddlayerd.domain.order.ModelMapper.mapDetails;


@AllArgsConstructor
public class OrderCommandAdapter implements OrderCommandPort {

    private static final String ITEM_ADDED = "Item added";
    private final OrderRepositoryPort orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public OrderDetailsVM addItem(Long orderId, AddItemCommand addItemCommand) {
        final Order foundedOrder = findOrderById(orderId);
        foundedOrder.addItem(addItemCommand);
        final var persistedOrder = orderRepository.save(foundedOrder);
        orderEventPublisher.publish(new ItemAdded(ITEM_ADDED, persistedOrder));
        return mapDetails(persistedOrder);
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
        removeOrderItem(itemId, foundedOrder);
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


    private void removeOrderItem(Long itemId, Order foundedOrder) {
        final var numberOfItemsBeforeDeletion = foundedOrder.getOrderItems().size();
        foundedOrder.getOrderItems()
                .removeIf(orderItem -> Objects.equals(orderItem.getId(), itemId));
        if (numberOfItemsBeforeDeletion == foundedOrder.getOrderItems().size()) {
            throw new OderItemNotFoundException(itemId);
        }
    }
}
