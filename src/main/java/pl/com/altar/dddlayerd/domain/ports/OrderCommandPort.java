package pl.com.altar.dddlayerd.domain.ports;

import pl.com.altar.dddlayerd.domain.OrderDetails;

public interface OrderCommandPort {

    void createOrder(OrderDetails orderDetails);

    void updateOrder(OrderDetails orderDetails);

    void deleteOrder(Long orderId);
}
