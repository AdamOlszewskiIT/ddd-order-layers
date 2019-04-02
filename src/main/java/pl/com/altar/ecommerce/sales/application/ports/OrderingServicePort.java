package pl.com.altar.ecommerce.sales.application.ports;

import pl.com.altar.ecommerce.sales.client.command.AddProductToOrderCommand;

public interface OrderingServicePort {
    Long createOrder();
    void addProduct(AddProductToOrderCommand command);
}
