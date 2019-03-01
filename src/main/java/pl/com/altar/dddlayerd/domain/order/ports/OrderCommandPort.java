package pl.com.altar.dddlayerd.domain.order.ports;


import pl.com.altar.dddlayerd.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;

public interface OrderCommandPort {

    OrderDetailsVM addItem(Long orderId, AddItemCommand addItemCommand);

    void deleteOrder(Long orderId);

    void deleteOrderItem(Long orderId, Long itemId);

    void deleteAll();
}
