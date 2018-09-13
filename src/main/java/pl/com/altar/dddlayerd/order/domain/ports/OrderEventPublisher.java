package pl.com.altar.dddlayerd.order.domain.ports;

import pl.com.altar.dddlayerd.order.domain.base.BaseEvent;

public interface OrderEventPublisher {
    void publish(BaseEvent baseEvent);
}
