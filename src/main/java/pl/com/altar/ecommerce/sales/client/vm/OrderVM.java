package pl.com.altar.ecommerce.sales.client.vm;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class OrderVM {
    private Long id;
    private String orderState;
    private String name;
    private BigDecimal price;
    private String serialNumber;
}
