package pl.com.altar.dddlayerd.order.domain;


import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.order.client.client_exception.OderNotFoundException;
import pl.com.altar.dddlayerd.order.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.order.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;
import pl.com.altar.dddlayerd.order.domain.events.ItemAdded;
import pl.com.altar.dddlayerd.order.domain.events.OrderCreated;
import pl.com.altar.dddlayerd.order.domain.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.order.domain.ports.OrderEventPublisher;
import pl.com.altar.dddlayerd.order.domain.ports.OrderQueryPort;

import java.util.Optional;

import static pl.com.altar.dddlayerd.order.domain.ModelMapper.map;
import static pl.com.altar.dddlayerd.order.domain.ModelMapper.mapDetails;

@Service
@AllArgsConstructor
class OrderServiceAdapter implements OrderCommandPort, OrderQueryPort {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public OrderVM createOrder(CreateOrderCommand createOrderCommand) {
        var newOrder = Order.createOrder(createOrderCommand);
        val persistedOrder = orderRepository.save(newOrder);
        OrderCreated orderCreated = new OrderCreated("Order created", persistedOrder);
        orderEventPublisher.publish(orderCreated);
        return map(persistedOrder);
    }

    @Override
    public OrderDetailsVM addItem(Long orderId, AddItemCommand addItemCommand) {
        Order foundedOrder = orderRepository.getOne(orderId)
                .orElseThrow(() -> new OderNotFoundException(orderId));
        foundedOrder.addItem(addItemCommand);
        val persistedOrder = orderRepository.save(foundedOrder);
        ItemAdded itemAdded = new ItemAdded("Item added", persistedOrder);
        orderEventPublisher.publish(itemAdded);
        return mapDetails(persistedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order foundedOrder = orderRepository.getOne(orderId)
                .orElseThrow(() -> new OderNotFoundException(orderId));
        foundedOrder.archive();
        orderRepository.save(foundedOrder);
    }

    @Override
    public OrderVM findOrder(Long orderId) {
        Optional<Order> orderOptional = orderRepository.getOne(orderId);
        return map(orderOptional.orElseThrow(() -> new OderNotFoundException(orderId)));
    }

}
