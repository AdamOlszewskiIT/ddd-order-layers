package pl.com.altar.dddlayerd.order.domain.exceptions;

public class OrderOperationException extends RuntimeException {
    public OrderOperationException(String message) {
        super(message);
    }
}
