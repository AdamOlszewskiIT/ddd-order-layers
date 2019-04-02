package pl.com.altar.ecommerce.sales.infrastructure.persistence.reservation;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.com.altar.ecommerce.sales.client.exception.ResourceNotFoundException;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationData;

import static pl.com.altar.ecommerce.shared.ExceptionUtil.handleOption;

@Repository
@AllArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepositoryPort {

    private final ReservationRepository reservationRepository;

    @Override
    public ReservationData save(ReservationData reservation) {
        return reservationRepository.save(new ReservationEntity(reservation));
    }

    @Override
    public ReservationData findById(Long reservationId) {
        return handleOption(reservationRepository.findById(reservationId), new ResourceNotFoundException(this.getClass().getName(), reservationId));

    }
}
