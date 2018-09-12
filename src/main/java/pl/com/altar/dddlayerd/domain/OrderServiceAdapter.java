package pl.com.altar.dddlayerd.domain;


import lombok.AllArgsConstructor;
import lombok.val;
import lombok.var;
import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.domain.events.OrderCreated;
import pl.com.altar.dddlayerd.domain.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.domain.ports.OrderEventPublisher;
import pl.com.altar.dddlayerd.domain.ports.OrderQueryPort;
import pl.com.altar.dddlayerd.domain.ports.OrderRepository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class OrderServiceAdapter implements OrderCommandPort, OrderQueryPort {

    private final OrderRepository orderRepository;
    private final OrderEventPublisher orderEventPublisher;

    @Override
    public void createOrder(OrderDetails orderDetails) {
        var newOrder = new Order();
        newOrder.createOrder(orderDetails);
        val persistedOrder = orderRepository.save(newOrder);
        OrderCreated orderCreated = new OrderCreated("Order created", persistedOrder);
        orderEventPublisher.publish(orderCreated);
    }

    @Override
    /* TODO create 'update order' method */
    public void updateOrder(OrderDetails orderDetails) {
        throw new NotImplementedException();
    }

    @Override
    /* TODO create 'delete order' method */
    public void deleteOrder(Long orderId) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<Order> findOrder(Long orderId) {
        return orderRepository.getOne(orderId);
    }

}
