package pl.com.altar.ecommerce.sales.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.shared.Quantity;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderItemProjection;

@NoArgsConstructor
@AllArgsConstructor
@Getter
class OrderItem implements OrderItemProjection {

    private Long id;

    private String name;

    private Quantity quantity;

    private Money price = Money.ZERO();

    private OrderItem(String name, Quantity quantity, Money price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    OrderItem(String name, double price, Integer quantity) {
        this(name, new Quantity(quantity), new Money(price));
    }

    Money getTotalPrice() {
        return this.price.multiplyBy(this.quantity.getNumberOfElements());
    }

}
