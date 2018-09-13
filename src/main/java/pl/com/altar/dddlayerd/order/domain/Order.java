package pl.com.altar.dddlayerd.order.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.dddlayerd.order.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.order.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.order.domain.exceptions.OrderOperationException;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "order_table")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    private String name;
    @Embedded
    private Money price;

    private String serialNumber = UUID.randomUUID().toString();

    private Timestamp submitDate;

    private OrderState orderState;

    public Order(String name, Money price, OrderState orderState) {
        this.name = name;
        this.price = price;
        this.orderState = orderState;
    }

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public static Order createOrder(CreateOrderCommand command) {
        return new Order(command.getName(), Money.ZERO, OrderState.DRAFT);
    }

    public void addItem(AddItemCommand command) {
        checkIfDraft();
        OrderItem newItem = OrderItem.createItem(
                command.getName(),
                command.getPrice(),
                command.getQuantity(),
                this
        );
        this.orderItems.add(newItem);
        recalculatePrice();
    }

    public void submit() {
        checkIfDraft();
        this.orderState = OrderState.SUBMITTED;
        this.submitDate = new Timestamp(System.currentTimeMillis());
    }

    public void archive() {
        this.orderState = OrderState.ARCHIVED;
    }

    private void checkIfDraft() {
        if (orderState != OrderState.DRAFT) {
            throw new OrderOperationException("Operation allowed only in DRAFT status");
        }
    }

    private void recalculatePrice() {
        this.price = Money.ZERO;
        orderItems.forEach(item -> this.price.add(item.getPrice()));
    }

    public void clearOrderItems() {
        this.orderItems.clear();
        recalculatePrice();
    }


}
