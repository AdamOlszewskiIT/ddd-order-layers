package pl.com.altar.dddlayerd.order.infrastructure;

import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.order.domain.base.BaseEvent;
import pl.com.altar.dddlayerd.order.domain.ports.OrderEventPublisher;

@Service
class OrderEventPublisherAdapter implements OrderEventPublisher {
    @Override
    public void publish(BaseEvent baseEvent) {
        System.out.println("Publishing event with message " + baseEvent.getMessage() + " of type " + baseEvent.eventType());
    }
}
