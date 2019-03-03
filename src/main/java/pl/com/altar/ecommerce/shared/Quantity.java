package pl.com.altar.ecommerce.shared;

import lombok.Getter;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quantity quantity = (Quantity) o;
        return Objects.equals(numberOfElements, quantity.numberOfElements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numberOfElements);
    }
}
