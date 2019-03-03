package pl.com.altar.ecommerce.sales.domain.order.events;


import pl.com.altar.ecommerce.shared.BaseEvent;

public class OrderCreated extends BaseEvent {
    public OrderCreated(String message, Object target) {
        super(message, target);
    }
}
