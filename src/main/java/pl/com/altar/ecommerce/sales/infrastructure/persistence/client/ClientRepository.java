package pl.com.altar.ecommerce.sales.infrastructure.persistence.client;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
