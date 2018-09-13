package pl.com.altar.dddlayerd.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.PackagePrivate;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "order_item_table")
@PackagePrivate
class OrderItem {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long id;

    private String name;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Money price = Money.ZERO;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    public OrderItem(String name, Quantity quantity, Money price, Order order) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.order = order;
    }

    public static OrderItem createItem(String name, double price, Integer quantity, Order order) {
        Quantity itemQuantity = new Quantity(quantity);
        Money priceMoney = new Money(price);
        return new OrderItem(name, itemQuantity, priceMoney, order);
    }

}
