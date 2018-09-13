package pl.com.altar.dddlayerd.order.domain.events;


import pl.com.altar.dddlayerd.order.domain.base.BaseEvent;


public class OrderCreated extends BaseEvent {
    public OrderCreated(String message, Object target) {
        super(message, target);
    }
}
