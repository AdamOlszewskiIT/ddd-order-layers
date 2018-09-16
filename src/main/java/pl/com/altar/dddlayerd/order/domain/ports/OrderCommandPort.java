package pl.com.altar.dddlayerd.order.domain.ports;

import pl.com.altar.dddlayerd.order.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.order.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;

public interface OrderCommandPort {

    OrderVM createOrder(CreateOrderCommand createOrderCommand);

    OrderDetailsVM addItem(Long orderId, AddItemCommand addItemCommand);

    void deleteOrder(Long orderId);

    void deleteAll();
}
