package pl.com.altar.ecommerce.sales.domain.order.projections;

import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.shared.Quantity;

public interface OrderItemProjection {
    Long getId();
    String getName();
    Quantity getQuantity();
    Money getPrice();
}
