package pl.com.altar.dddlayerd.order.domain.events;


import pl.com.altar.dddlayerd.order.domain.base.BaseEvent;


public class ItemAdded extends BaseEvent {
    public ItemAdded(String message, Object target) {
        super(message, target);
    }
}
