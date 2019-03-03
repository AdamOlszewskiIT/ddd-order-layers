package pl.com.altar.ecommerce.sales.client.vm;

import lombok.Value;

@Value
public class OrderItemVM {
    private Long id;
    private String name;
    private double price;
    private Integer quantity;
}
