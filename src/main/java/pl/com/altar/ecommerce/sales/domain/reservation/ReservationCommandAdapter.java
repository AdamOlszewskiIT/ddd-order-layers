package pl.com.altar.ecommerce.sales.domain.reservation;

import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationCommandPort;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationRepositoryPort;
import pl.com.altar.ecommerce.shared.Quantity;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainService;

@DomainService
@AllArgsConstructor
public class ReservationCommandAdapter implements ReservationCommandPort {

    private final ReservationRepositoryPort reservationRepositoryPort;

    @Override
    public void addProductToReservation(Long orderId, ProductData product, Quantity quantity) {
        Reservation reservation = new Reservation(reservationRepositoryPort.findById(orderId));
        reservation.add(product, quantity);
        reservationRepositoryPort.save(reservation);
    }

}
