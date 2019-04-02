package pl.com.altar.ecommerce.sales.domain.purchase;

import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.client.command.CreatePurchaseCommand;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseFactoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainFactory;
import pl.com.altar.ecommerce.shared.support.DomainEventPublisher;

@DomainFactory
@AllArgsConstructor
public class PurchaseFactoryAdapter implements PurchaseFactoryPort {
    private final PurchaseRepositoryPort orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public PurchaseData createPurchase(CreatePurchaseCommand createPurchaseCommand) {
        var newOrder = new Purchase(createPurchaseCommand);
        return orderRepository.save(newOrder);
    }
}
