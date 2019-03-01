package pl.com.altar.dddlayerd.infrastructure.repository;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.dddlayerd.application.shared.Money;
import pl.com.altar.dddlayerd.application.shared.Quantity;
import pl.com.altar.dddlayerd.domain.order.projections.OrderItemProjection;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "order_item_table")
public class OrderItemEntity implements OrderItemProjection {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long id;

    private String name;

    @Embedded
    private Quantity quantity;

    @Embedded
    private Money price = Money.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;
}
