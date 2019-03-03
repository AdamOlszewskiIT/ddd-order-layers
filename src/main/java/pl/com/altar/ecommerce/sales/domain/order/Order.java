package pl.com.altar.ecommerce.sales.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.client.exception.OderItemNotFoundException;
import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.command.CreateOrderCommand;
import pl.com.altar.ecommerce.sales.domain.order.exceptions.OrderOperationException;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderItemProjection;
import pl.com.altar.ecommerce.sales.domain.order.projections.OrderProjection;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.com.altar.ecommerce.sales.domain.order.OrderState.*;

@Getter
@AllArgsConstructor
class Order implements OrderProjection {

    private static final String OPERATION_ALLOWED_ONLY_IN_DRAFT_STATUS = "Operation allowed only in DRAFT status";
    private Long id;
    private String name;
    private Money price;
    private String serialNumber = UUID.randomUUID().toString();
    private Timestamp submitDate;
    private OrderState orderState;

    private Order(String name, Money price, OrderState orderState) {
        this.name = name;
        this.price = price;
        this.orderState = orderState;
    }

    private List<OrderItem> orderItems = new ArrayList<>();

    Order(CreateOrderCommand command) {
        this(command.getName(), Money.ZERO(), DRAFT);
    }

    void addItem(AddItemCommand command) {
        checkIfDraft();
        final var newItem = new OrderItem(
                command.getName(),
                command.getPrice(),
                command.getQuantity()
        );
        this.orderItems.add(newItem);
        recalculatePrice();
    }

    void submit() {
        checkIfDraft();
        this.orderState = SUBMITTED;
        this.submitDate = new Timestamp(System.currentTimeMillis());
    }

    void archive() {
        this.orderState = ARCHIVED;
    }

    private void checkIfDraft() {
        if (orderState != DRAFT) {
            throw new OrderOperationException(OPERATION_ALLOWED_ONLY_IN_DRAFT_STATUS);
        }
    }

    private void recalculatePrice() {
        this.price = Money.ZERO();
        orderItems.forEach(item -> this.price = this.price.add(item.getTotalPrice()));
    }

    void clearOrderItems() {
        this.orderItems.clear();
        recalculatePrice();
    }

     void removeOrderItem(Long itemId) {
        final var numberOfItemsBeforeDeletion = this.getOrderItems().size();
        this.getOrderItems()
                .removeIf(orderItem -> Objects.equals(orderItem.getId(), itemId));
        if (numberOfItemsBeforeDeletion == this.getOrderItems().size()) {
            throw new OderItemNotFoundException(itemId);
        }
        recalculatePrice();
    }

    private static OrderItem of(OrderItemProjection oip) {
        return new OrderItem(
                oip.getId(),
                oip.getName(),
                oip.getQuantity(),
                oip.getPrice()
        );
    }

    Order(OrderProjection op) {
        this(
                op.getId(),
                op.getName(),
                op.getPrice(),
                op.getSerialNumber(),
                op.getSubmitDate(),
                valueOf(op.getOrderStateName()),
                null
        );
        this.orderItems = parse(op.getOrderItemProjections());
    }

    private static List<OrderItem> parse(List<OrderItemProjection> orderItemProjections) {
        return orderItemProjections
                .stream()
                .map(Order::of)
                .collect(Collectors.toList());
    }

    @Override
    public String getOrderStateName() {
        return this.orderState.name();
    }

    @Override
    public List<OrderItemProjection> getOrderItemProjections() {
        return new ArrayList<>(this.orderItems);
    }
}
