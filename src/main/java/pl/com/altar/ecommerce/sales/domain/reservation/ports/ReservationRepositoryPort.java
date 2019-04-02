package pl.com.altar.ecommerce.sales.domain.reservation.ports;

import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationData;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainRepository;

@DomainRepository
public interface ReservationRepositoryPort {
    ReservationData save(ReservationData reservation);
    ReservationData findById(Long reservationId);
}
