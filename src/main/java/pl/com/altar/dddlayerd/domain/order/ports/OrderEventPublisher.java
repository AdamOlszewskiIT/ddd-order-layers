package pl.com.altar.dddlayerd.domain.order.ports;

import pl.com.altar.dddlayerd.application.shared.BaseEvent;

public interface OrderEventPublisher {
    void publish(BaseEvent baseEvent);
}
