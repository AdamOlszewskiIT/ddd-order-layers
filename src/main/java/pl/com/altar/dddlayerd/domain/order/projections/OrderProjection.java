package pl.com.altar.dddlayerd.domain.order.projections;

import pl.com.altar.dddlayerd.application.shared.Money;
import pl.com.altar.dddlayerd.domain.order.projections.OrderItemProjection;

import java.sql.Timestamp;
import java.util.List;

public interface OrderProjection {
    Long getId();
    String getName();
    Money getPrice();
    String getSerialNumber();
    Timestamp getSubmitDate();
    String getOrderStateName();
    List<OrderItemProjection> getOrderItemProjections();
}
