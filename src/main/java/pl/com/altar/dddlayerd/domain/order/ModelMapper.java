package pl.com.altar.dddlayerd.domain.order;


import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.client.vm.OrderItemVM;
import pl.com.altar.dddlayerd.client.vm.OrderVM;
import pl.com.altar.dddlayerd.domain.order.projections.OrderItemProjection;
import pl.com.altar.dddlayerd.domain.order.projections.OrderProjection;

import java.util.stream.Collectors;

class ModelMapper {


    static OrderVM map(OrderProjection order) {
        return new OrderVM(
                order.getId(),
                order.getOrderStateName(),
                order.getName(),
                order.getPrice().getMoney(),
                order.getSerialNumber()
        );
    }

    static OrderItemVM map(OrderItemProjection item) {
        return new OrderItemVM(
                item.getId(),
                item.getName(),
                item.getPrice().getMoney().doubleValue(),
                item.getQuantity().getNumberOfElements()
        );
    }

    static OrderDetailsVM mapDetails(OrderProjection order) {
        return new OrderDetailsVM(
                order.getId(),
                order.getOrderStateName(),
                order.getName(),
                order.getPrice().getMoney(),
                order.getSerialNumber(),
                order.getOrderItemProjections()
                        .stream()
                .map(ModelMapper::map)
                .collect(Collectors.toList())
        );
    }

}
