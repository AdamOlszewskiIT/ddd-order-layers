package pl.com.altar.ecommerce.sales.domain.reservation.ports;

import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.shared.Quantity;

public interface ReservationCommandPort {
    void addProductToReservation(Long orderId, ProductData product, Quantity quantity);
}
