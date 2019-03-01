package pl.com.altar.dddlayerd.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.dddlayerd.application.shared.Money;
import pl.com.altar.dddlayerd.domain.order.projections.OrderItemProjection;
import pl.com.altar.dddlayerd.domain.order.projections.OrderProjection;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
@Table(name = "order_table")
@AllArgsConstructor
public class OrderEntity implements OrderProjection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    private String name;

    @Embedded
    private Money price = Money.ZERO;

    private String serialNumber = UUID.randomUUID().toString();

    private Timestamp submitDate;

    private String orderState;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderItemEntity> orderItems = new ArrayList<>();

    static OrderItemEntity of(OrderItemProjection oip, OrderEntity oe) {
        return new OrderItemEntity(
                oip.getId(),
                oip.getName(),
                oip.getQuantity(),
                oip.getPrice(),
                oe
        );
    }

    public OrderEntity(OrderProjection op) {
        this(
                op.getId(),
                op.getName(),
                op.getPrice(),
                op.getSerialNumber(),
                op.getSubmitDate(),
                op.getOrderStateName(),
                null
                );
        this.orderItems = parse(op.getOrderItemProjections(), this);
    }

    private static List<OrderItemEntity> parse(List<OrderItemProjection> orderItemProjections, OrderEntity oe) {
        return orderItemProjections
                .stream()
                .map(op -> of(op, oe))
                .collect(Collectors.toList());
    }


    @Override
    public String getOrderStateName() {
        return this.orderState;
    }

    @Override
    public List<OrderItemProjection> getOrderItemProjections() {
        return new ArrayList<>(this.orderItems);
    }
}
