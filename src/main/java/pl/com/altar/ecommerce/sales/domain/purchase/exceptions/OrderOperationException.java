package pl.com.altar.ecommerce.sales.domain.purchase.exceptions;

public class OrderOperationException extends RuntimeException {
    public OrderOperationException(String message) {
        super(message);
    }
}
