package pl.com.altar.ecommerce.sales.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationItemData;
import pl.com.altar.ecommerce.shared.Quantity;
import pl.com.altar.ecommerce.shared.annotations.domain.Aggregate;

@Getter
@Aggregate
@AllArgsConstructor
class ReservationItem implements ReservationItemData {
    private Long id;
    private ProductData product;
    private Quantity quantity;

    protected ReservationItem(ReservationItemData reservationItemData) {
        this(
                reservationItemData.getId(),
                reservationItemData.getProduct(),
                new Quantity(reservationItemData.getQuantityValue())
        );
    }
    protected void changeQuantity(Quantity change) {
        this.quantity = new Quantity(
                quantity.getNumberOfElements() + change.getNumberOfElements())
        ;
    }
    @Override
    public Integer getQuantityValue() {
        return quantity.getNumberOfElements();
    }
}
