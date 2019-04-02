package pl.com.altar.ecommerce.sales.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.altar.ecommerce.sales.domain.reservation.ReservationCommandAdapter;
import pl.com.altar.ecommerce.sales.domain.reservation.ReservationFactoryAdapter;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationCommandPort;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationFactoryPort;
import pl.com.altar.ecommerce.sales.infrastructure.persistence.reservation.ReservationRepositoryAdapter;

@Configuration
public class ReservationConfiguration {

    @Bean
    public ReservationFactoryPort getReservationFactory(ReservationRepositoryAdapter reservationRepositoryAdapter) {
        return new ReservationFactoryAdapter(reservationRepositoryAdapter);
    }

    @Bean
    public ReservationCommandPort getReservationCommand(ReservationRepositoryAdapter reservationRepositoryAdapter) {
        return new ReservationCommandAdapter(reservationRepositoryAdapter);
    }

}
