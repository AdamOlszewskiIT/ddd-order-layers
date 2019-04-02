package pl.com.altar.ecommerce.sales.domain.reservation;

import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationFactoryPort;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationData;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainFactory;
import pl.com.altar.ecommerce.shared.exceptions.DomainOperationException;

import java.util.ArrayList;
import java.util.Date;

import static pl.com.altar.ecommerce.sales.domain.SalesMessages.CLIENT_CAN_NOT_CREATE_RESERVATIONS;

@DomainFactory
@AllArgsConstructor
public class ReservationFactoryAdapter implements ReservationFactoryPort {

    private final ReservationRepositoryPort reservationRepositoryPort;

    @Override
    public ReservationData create(ClientData clientData) {
        checkClientAbilityToReservation(clientData);
        Reservation reservation = new Reservation(null, ReservationStatus.OPENED, new ArrayList<>(), clientData, new Date());
        addGratis(reservation, clientData);
        return reservationRepositoryPort.save(reservation);
    }

    private void checkClientAbilityToReservation(ClientData clientData) {
        if(!clientData.canAfford()) {
            throw new DomainOperationException(clientData.getId(), CLIENT_CAN_NOT_CREATE_RESERVATIONS);
        }
    }

    private void addGratis(Reservation reservation, ClientData client) {
        //TODO add gratis policy
    }

}
