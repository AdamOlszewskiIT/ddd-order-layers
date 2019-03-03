package pl.com.altar.ecommerce.sales.domain.order.ports;

import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

import java.util.List;

public interface OrderRepositoryPort {

    OrderProjection save(OrderProjection order);

    OrderProjection findById(Long id);

    List<OrderProjection> findAll();

    void deleteAll();

    Long count();

}
