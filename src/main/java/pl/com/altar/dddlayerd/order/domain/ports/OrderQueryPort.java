package pl.com.altar.dddlayerd.order.domain.ports;

import pl.com.altar.dddlayerd.order.client.vm.OrderVM;

public interface OrderQueryPort {

    OrderVM findOrder(Long orderId);

}
