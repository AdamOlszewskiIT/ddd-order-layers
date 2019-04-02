package pl.com.altar.ecommerce.sales.client.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    private static final String message = "%s with id %s does not exist.";

    public ResourceNotFoundException(String object, Long clientId) {
        super(String.format(message, object, clientId));
    }
}
