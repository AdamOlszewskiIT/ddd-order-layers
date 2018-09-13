package pl.com.altar.dddlayerd.order.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@NoArgsConstructor
final class Quantity {
    private Integer numberOfElements;

    Quantity(Integer numberOfElements) {
        if (numberOfElements < 0) {
            throw new IllegalArgumentException("Number of elements cannot be lower than 0");
        }
        this.numberOfElements = numberOfElements;
    }

}
