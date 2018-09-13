package pl.com.altar.dddlayerd.order.domain;

import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderItemVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;

import java.util.stream.Collectors;

class ModelMapper {


    static OrderVM map(Order order) {
        return new OrderVM(
                order.getId(),
                order.getOrderState().name(),
                order.getName(),
                order.getPrice().getMoney(),
                order.getSerialNumber()
        );
    }

    static OrderItemVM map(OrderItem item) {
        return new OrderItemVM(
                item.getName(),
                item.getPrice().getMoney().doubleValue(),
                item.getQuantity().getNumberOfElements()
        );
    }

    static OrderDetailsVM mapDetails(Order order) {
        return new OrderDetailsVM(
                order.getId(),
                order.getOrderState().name(),
                order.getName(),
                order.getPrice().getMoney(),
                order.getSerialNumber(),
                order.getOrderItems()
                        .stream()
                .map(ModelMapper::map)
                .collect(Collectors.toList())
        );
    }

}
