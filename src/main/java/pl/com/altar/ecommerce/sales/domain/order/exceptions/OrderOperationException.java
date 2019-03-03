package pl.com.altar.ecommerce.sales.domain.order.exceptions;

public class OrderOperationException extends RuntimeException {
    public OrderOperationException(String message) {
        super(message);
    }
}
