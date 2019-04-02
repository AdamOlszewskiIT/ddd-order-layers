package pl.com.altar.ecommerce.sales.client;


import pl.com.altar.ecommerce.sales.client.vm.OrderDetailsVM;
import pl.com.altar.ecommerce.sales.client.vm.OrderItemVM;
import pl.com.altar.ecommerce.sales.client.vm.OrderVM;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseItemData;

import java.util.List;
import java.util.stream.Collectors;

public class ModelMapper {


    public static OrderVM map(PurchaseData order) {
        return new OrderVM(
                order.getId(),
                order.getOrderStateName(),
                order.getName(),
                order.getTotalCost().getDenomination(),
                order.getSerialNumber()
        );
    }

    public static List<OrderVM> map(List<PurchaseData> purchaseData) {
        return purchaseData.stream()
                .map(ModelMapper::map)
                .collect(Collectors.toList());
    }

    public static OrderItemVM map(PurchaseItemData item) {
        return new OrderItemVM(
                item.getId(),
                item.getName(),
                item.getPrice().getDenomination().doubleValue(),
                item.getQuantity().getNumberOfElements()
        );
    }

    public static OrderDetailsVM mapDetails(PurchaseData order) {
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
