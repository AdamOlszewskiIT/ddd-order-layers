package pl.com.altar.dddlayerd.domain.order.events;


import pl.com.altar.dddlayerd.application.shared.BaseEvent;

public class ItemAdded extends BaseEvent {
    public ItemAdded(String message, Object target) {
        super(message, target);
    }
}
