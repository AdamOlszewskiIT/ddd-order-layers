package pl.com.altar.dddlayerd.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.dddlayerd.application.shared.Money;
import pl.com.altar.dddlayerd.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.domain.order.exceptions.OrderOperationException;
import pl.com.altar.dddlayerd.domain.order.projections.OrderItemProjection;
import pl.com.altar.dddlayerd.domain.order.projections.OrderProjection;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.com.altar.dddlayerd.domain.order.OrderState.*;

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

    static Order createOrder(CreateOrderCommand command) {
        return new Order(command.getName(), Money.ZERO, DRAFT);
    }

    void addItem(AddItemCommand command) {
        checkIfDraft();
        final var newItem = OrderItem.createItem(
                command.getName(),
                command.getPrice(),
                command.getQuantity(),
                this
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
        this.price = Money.ZERO;
        orderItems.forEach(item -> this.price.add(item.getPrice()));
    }

    void clearOrderItems() {
        this.orderItems.clear();
        recalculatePrice();
    }

    private static OrderItem of(OrderItemProjection oip, Order oe) {
        return new OrderItem(
                oip.getId(),
                oip.getName(),
                oip.getQuantity(),
                oip.getPrice(),
                oe
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
        this.orderItems = parse(op.getOrderItemProjections(), this);
    }

    private static List<OrderItem> parse(List<OrderItemProjection> orderItemProjections, Order order) {
        return orderItemProjections
                .stream()
                .map(oip -> of(oip, order))
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
