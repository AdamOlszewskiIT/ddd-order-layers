package pl.com.altar.ecommerce.sales.domain.order.ports;


import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.vm.OrderDetailsVM;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

public interface OrderCommandPort {

    OrderProjection addItem(Long orderId, AddItemCommand addItemCommand);

    void deleteOrder(Long orderId);

    void deleteOrderItem(Long orderId, Long itemId);

    void deleteAll();
}
