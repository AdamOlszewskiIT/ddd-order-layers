package pl.com.altar.ecommerce.sales.infrastructure.persistence.client;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import pl.com.altar.ecommerce.sales.client.exception.ResourceNotFoundException;
import pl.com.altar.ecommerce.sales.domain.client.ports.ClientRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;

import static pl.com.altar.ecommerce.shared.ExceptionUtil.handleOption;

@Repository
@AllArgsConstructor
public class ClientRepositoryPortAdapter implements ClientRepositoryPort {

    private final ClientRepository repository;

    @Override
    public ClientData save(ClientData clientData) {
        return repository.save(new ClientEntity(clientData));
    }

    @Override
    public ClientData findById(Long clientId) {
        return handleOption(repository.findById(clientId), new ResourceNotFoundException(this.getClass().getName(), clientId));
    }
}
