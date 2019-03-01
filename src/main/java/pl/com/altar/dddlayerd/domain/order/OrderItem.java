package pl.com.altar.dddlayerd.domain.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.dddlayerd.application.shared.Money;
import pl.com.altar.dddlayerd.application.shared.Quantity;
import pl.com.altar.dddlayerd.domain.order.projections.OrderItemProjection;

import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Data
class OrderItem implements OrderItemProjection {

    private Long id;

    private String name;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Money price = Money.ZERO;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    private OrderItem(String name, Quantity quantity, Money price, Order order) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    static OrderItem createItem(String name, double price, Integer quantity, Order order) {
        final var  itemQuantity = new Quantity(quantity);
        final var  priceMoney = new Money(price);
        return new OrderItem(name, itemQuantity, priceMoney, order);
    }

}
