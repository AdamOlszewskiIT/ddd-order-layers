package pl.com.altar.ecommerce.sales.domain.order.projections;

import pl.com.altar.ecommerce.shared.Money;

import java.sql.Timestamp;
import java.util.List;

public interface OrderProjection {
    Long getId();
    String getName();
    Money getTotalCost();
    String getSerialNumber();
    Timestamp getSubmitDate();
    String getOrderStateName();
    List<OrderItemProjection> getOrderItemProjections();
}
