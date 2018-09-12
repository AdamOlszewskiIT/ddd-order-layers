package pl.com.altar.dddlayerd.domain.ports;

import pl.com.altar.dddlayerd.domain.base.BaseEvent;

public interface OrderEventPublisher {
    void publish(BaseEvent baseEvent);
}
