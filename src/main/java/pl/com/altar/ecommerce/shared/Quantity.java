package pl.com.altar.ecommerce.shared;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.Embeddable;

@Getter
@Embeddable
@EqualsAndHashCode
public final class Quantity {
    private final Integer numberOfElements;

    public Quantity() {
        this(0);
    }

    public Quantity(Integer numberOfElements) {
        if (numberOfElements < 0) {
            throw new IllegalArgumentException("Number of elements cannot be lower than 0");
        }
        this.numberOfElements = numberOfElements;
    }
}
