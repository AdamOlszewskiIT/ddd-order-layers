package pl.com.altar.ecommerce.sales.domain.order.events;


import pl.com.altar.ecommerce.shared.BaseEvent;

public class ItemAdded extends BaseEvent {
    public ItemAdded(String message, Object target) {
        super(message, target);
    }
}
