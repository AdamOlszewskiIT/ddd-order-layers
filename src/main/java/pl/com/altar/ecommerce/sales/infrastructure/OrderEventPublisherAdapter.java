package pl.com.altar.ecommerce.sales.infrastructure;

import org.springframework.stereotype.Service;
import pl.com.altar.ecommerce.shared.BaseEvent;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderEventPublisher;


@Service
public class OrderEventPublisherAdapter implements OrderEventPublisher {
    @Override
    public void publish(BaseEvent baseEvent) {
        System.out.println("Publishing event with message " + baseEvent.getMessage() + " of type " + baseEvent.eventType());
    }
}
