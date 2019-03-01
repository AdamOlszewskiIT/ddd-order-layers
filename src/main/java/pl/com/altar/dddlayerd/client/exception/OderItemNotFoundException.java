package pl.com.altar.dddlayerd.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class OderItemNotFoundException extends RuntimeException {
    private static final String message = "Order item with id %s does not exist.";

    public OderItemNotFoundException(Long orderItemId) {
        super(String.format(message, orderItemId));
    }
}
