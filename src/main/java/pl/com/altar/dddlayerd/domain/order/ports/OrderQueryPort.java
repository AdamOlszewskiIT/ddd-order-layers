package pl.com.altar.dddlayerd.domain.order.ports;


import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.client.vm.OrderVM;

import java.util.List;

public interface OrderQueryPort {

    OrderDetailsVM findOrder(Long orderId);

    List<OrderVM> findAll();

    List<OrderDetailsVM> findAllWithDetails();

}
