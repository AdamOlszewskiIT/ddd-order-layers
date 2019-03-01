package pl.com.altar.dddlayerd.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OderNotFoundException extends RuntimeException {
    private static final String message = "Order with id %s does not exist.";

    public OderNotFoundException(Long orderId) {
        super(String.format(message, orderId));
    }
}
