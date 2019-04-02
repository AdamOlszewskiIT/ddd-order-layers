package pl.com.altar.ecommerce.sales.domain.reservation.ports;

import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationData;

public interface ReservationFactoryPort {
    ReservationData create(ClientData clientData);
}
