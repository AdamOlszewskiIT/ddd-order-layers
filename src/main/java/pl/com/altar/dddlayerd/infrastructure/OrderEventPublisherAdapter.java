package pl.com.altar.dddlayerd.infrastructure;

import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.application.shared.BaseEvent;
import pl.com.altar.dddlayerd.domain.order.ports.OrderEventPublisher;


@Service
public class OrderEventPublisherAdapter implements OrderEventPublisher {
    @Override
    public void publish(BaseEvent baseEvent) {
        System.out.println("Publishing event with message " + baseEvent.getMessage() + " of type " + baseEvent.eventType());
    }
}
