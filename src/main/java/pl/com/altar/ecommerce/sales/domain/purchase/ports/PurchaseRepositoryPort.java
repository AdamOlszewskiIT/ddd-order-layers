package pl.com.altar.ecommerce.sales.domain.purchase.ports;

import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainRepository;

import java.util.List;

@DomainRepository
public interface PurchaseRepositoryPort {
    PurchaseData save(PurchaseData purchase);
    PurchaseData findById(Long purchaseId);
    List<PurchaseData> findAll();
    void deleteAll();
    Long count();
}
