package pl.com.altar.ecommerce.sales.infrastructure.persistence.purchase;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.com.altar.ecommerce.sales.client.exception.ResourceNotFoundException;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;

import java.util.ArrayList;
import java.util.List;

import static pl.com.altar.ecommerce.shared.ExceptionUtil.handleOption;

@Service
@AllArgsConstructor
public class PurchaseRepositoryAdapter implements PurchaseRepositoryPort {

    private final PurchaseRepository purchaseRepository;

    @Override
    public PurchaseData save(PurchaseData order) {
        final var orderEntity = new PurchaseEntity(order);
        return purchaseRepository.saveAndFlush(orderEntity);
    }

    @Override
    public PurchaseData findById(Long id) {
        return handleOption(purchaseRepository.findById(id), new ResourceNotFoundException(this.getClass().getName(), id));
    }

    @Override
    public List<PurchaseData> findAll() {
        return new ArrayList<>(purchaseRepository.findAll());
    }

    @Override
    public void deleteAll() {
        purchaseRepository.deleteAll();
    }

    @Override
    public Long count() {
        return purchaseRepository.count();
    }
}
