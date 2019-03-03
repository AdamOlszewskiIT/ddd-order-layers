package pl.com.altar.ecommerce.sales.domain.order.ports;

import pl.com.altar.ecommerce.sales.client.command.CreateOrderCommand;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

public interface OrderFactoryPort {

    OrderProjection createOrder(CreateOrderCommand createOrderCommand);

}
