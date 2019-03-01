package pl.com.altar.dddlayerd.domain.order.exceptions;

public class OrderOperationException extends RuntimeException {
    public OrderOperationException(String message) {
        super(message);
    }
}
