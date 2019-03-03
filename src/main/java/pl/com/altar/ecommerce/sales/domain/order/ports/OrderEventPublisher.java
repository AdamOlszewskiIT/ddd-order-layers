package pl.com.altar.ecommerce.sales.domain.order.ports;

import pl.com.altar.ecommerce.shared.BaseEvent;

public interface OrderEventPublisher {
    void publish(BaseEvent baseEvent);
}
