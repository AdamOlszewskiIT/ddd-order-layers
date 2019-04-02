package pl.com.altar.ecommerce.sales.domain.client.ports;

import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainRepository;

@DomainRepository
public interface ClientRepositoryPort {
    ClientData save(ClientData clientData);
    ClientData findById(Long clientId);
}
