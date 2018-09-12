package pl.com.altar.dddlayerd.infrastructure.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import pl.com.altar.dddlayerd.domain.base.BaseEvent;
import pl.com.altar.dddlayerd.domain.ports.OrderEventPublisher;

@Service
@Profile("default")
public class OrderEventPublisherAdapter implements OrderEventPublisher {
    @Override
    public void publish(BaseEvent baseEvent) {
        System.out.println("Publishing event with message " + baseEvent.getMessage() + " of type " + baseEvent.eventType());
    }
}
