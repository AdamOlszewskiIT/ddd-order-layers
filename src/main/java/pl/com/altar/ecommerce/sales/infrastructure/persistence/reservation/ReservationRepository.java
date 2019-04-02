package pl.com.altar.ecommerce.sales.infrastructure.persistence.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
