package pl.com.altar.dddlayerd.domain.events;


import pl.com.altar.dddlayerd.domain.base.BaseEvent;


public class OrderCreated extends BaseEvent {
    public OrderCreated(String message, Object target) {
        super(message, target);
    }
}
