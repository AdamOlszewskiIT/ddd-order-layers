package pl.com.altar.dddlayerd.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.dddlayerd.domain.base.EntityBase;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "order_table")
public class Order extends EntityBase<Order> {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @Embedded
    private OrderDetails orderDetails;

    private OrderState orderState;

    public Order createOrder(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
        this.orderState = OrderState.CREATED;
        return this;
    }

    public Order makeOrdered() {
        this.orderState = OrderState.ORDERED;
        return this;
    }

    public Order makeShipped() {
        this.orderState = OrderState.SHIPPED;
        return this;
    }

    public Order makeDelivered() {
        this.orderState = OrderState.DELIVERED;
        return this;
    }

    public Order updateOrderDetails(OrderDetails orderDetails) {
        this.orderDetails = orderDetails;
        return this;
    }

}
