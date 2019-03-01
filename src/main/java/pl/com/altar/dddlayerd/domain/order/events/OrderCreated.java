package pl.com.altar.dddlayerd.domain.order.events;


import pl.com.altar.dddlayerd.application.shared.BaseEvent;

public class OrderCreated extends BaseEvent {
    public OrderCreated(String message, Object target) {
        super(message, target);
    }
}
