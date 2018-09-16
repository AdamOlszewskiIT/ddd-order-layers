package pl.com.altar.dddlayerd.order.domain.ports;

import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;

import java.util.List;

public interface OrderQueryPort {

    OrderDetailsVM findOrder(Long orderId);

    List<OrderVM> findAll();

    List<OrderDetailsVM> findAllWithDetails();

}
