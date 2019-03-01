package pl.com.altar.dddlayerd.domain.order.ports;

import pl.com.altar.dddlayerd.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.client.vm.OrderVM;

public interface OrderFactoryPort {

    OrderVM createOrder(CreateOrderCommand createOrderCommand);

}
