package pl.com.altar.ecommerce.sales.domain.order.ports;


import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

import java.util.List;

public interface OrderQueryPort {

    OrderProjection findOrder(Long orderId);

    List<OrderProjection> findAll();

    List<OrderProjection> findAllWithDetails();

}
