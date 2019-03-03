package pl.com.altar.ecommerce.sales.client.vm;

import lombok.Value;

import java.math.BigDecimal;
import java.util.List;

@Value
public class OrderDetailsVM {
    private Long id;
    private String orderState;
    private String name;
    private BigDecimal price;
    private String serialNumber;
    private List<OrderItemVM> orderItemVMList;
}
