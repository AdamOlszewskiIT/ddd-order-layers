package pl.com.altar.ecommerce.sales.client;


import pl.com.altar.ecommerce.sales.client.vm.OrderDetailsVM;
import pl.com.altar.ecommerce.sales.client.vm.OrderItemVM;
import pl.com.altar.ecommerce.sales.client.vm.OrderVM;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderItemProjection;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {


    public static OrderVM map(OrderProjection order) {
        return new OrderVM(
                order.getId(),
                order.getOrderStateName(),
                order.getName(),
                order.getTotalCost().getDenomination(),
                order.getSerialNumber()
        );
    }

    public static List<OrderVM> map(List<OrderProjection> orderProjections) {
        return orderProjections.stream()
                .map(ModelMapper::map)
                .collect(Collectors.toList());
    }

    public static OrderItemVM map(OrderItemProjection item) {
        return new OrderItemVM(
                item.getId(),
                item.getName(),
                item.getPrice().getDenomination().doubleValue(),
                item.getQuantity().getNumberOfElements()
        );
    }

    public static OrderDetailsVM mapDetails(OrderProjection order) {
        return new OrderDetailsVM(
                order.getId(),
                order.getOrderStateName(),
                order.getName(),
                order.getTotalCost().getDenomination(),
                order.getSerialNumber(),
                order.getOrderItemProjections()
                        .stream()
                .map(ModelMapper::map)
                .collect(Collectors.toList())
        );
    }

}
