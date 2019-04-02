package pl.com.altar.ecommerce.sales.domain.purchase;


import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseQueryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;

import java.util.List;


@AllArgsConstructor
public class PurchaseQueryAdapter implements PurchaseQueryPort {

    private final PurchaseRepositoryPort orderRepository;

    @Override
    public PurchaseData findPurchase(Long orderId) {
        return orderRepository.findById(orderId);
    }

    @Override
    public List<PurchaseData> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public List<PurchaseData> findAllWithDetails() {
        return orderRepository.findAll();
    }

}
