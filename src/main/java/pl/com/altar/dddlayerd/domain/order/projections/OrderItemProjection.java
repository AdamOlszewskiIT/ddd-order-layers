package pl.com.altar.dddlayerd.domain.order.projections;

import pl.com.altar.dddlayerd.application.shared.Money;
import pl.com.altar.dddlayerd.application.shared.Quantity;

public interface OrderItemProjection {
    Long getId();
    String getName();
    Quantity getQuantity();
    Money getPrice();
    OrderProjection getOrder();
}
