package pl.com.altar.ecommerce.shared.exceptions;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class DomainOperationException extends RuntimeException{

    private Long id;

    public DomainOperationException(Long id, String message){
        super(message);
        this.id = id;
    }
}
