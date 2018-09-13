package pl.com.altar.dddlayerd.order.client.vm;

import lombok.Value;

@Value
public class OrderItemVM {
    private String name;
    private double price;
    private Integer quantity;
}
