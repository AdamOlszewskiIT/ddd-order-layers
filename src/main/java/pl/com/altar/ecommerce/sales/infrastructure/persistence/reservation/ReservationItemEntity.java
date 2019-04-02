package pl.com.altar.ecommerce.sales.infrastructure.persistence.reservation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationItemData;
import pl.com.altar.ecommerce.sales.infrastructure.persistence.product.ProductEntity;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "reservation_item_table")
@AllArgsConstructor
public class ReservationItemEntity implements ReservationItemData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "reservation_item_id")
    private Long id;
    @ManyToOne
    private ProductEntity product;
    private Integer quantityValue;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reservation_id", nullable = false)
    private ReservationEntity reservation;

    public ReservationItemEntity(ReservationItemData reservationItemData, ReservationEntity reservationEntity) {
        this(
                reservationItemData.getId(),
                new ProductEntity(reservationItemData.getProduct()),
                reservationItemData.getQuantityValue(),
                reservationEntity
        );
    }
}
