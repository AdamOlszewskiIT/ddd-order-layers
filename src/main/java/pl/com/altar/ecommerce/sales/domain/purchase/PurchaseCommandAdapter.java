package pl.com.altar.ecommerce.sales.domain.purchase;


import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.domain.purchase.events.PurchaseItemAdded;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseCommandPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.shared.annotations.domain.DomainService;
import pl.com.altar.ecommerce.shared.support.DomainEventPublisher;

@DomainService
@AllArgsConstructor
public class PurchaseCommandAdapter implements PurchaseCommandPort {
    private final PurchaseRepositoryPort orderRepository;
    private final DomainEventPublisher domainEventPublisher;

    @Override
    public PurchaseData addItem(Long orderId, AddItemCommand addItemCommand) {
        final Purchase foundedPurchase = findOrderById(orderId);
        final PurchaseItem purchaseItem = foundedPurchase.addItem(addItemCommand);
        domainEventPublisher.publish(new PurchaseItemAdded(orderId, purchaseItem));
        return orderRepository.save(foundedPurchase);
    }
    @Override
    public void deletePurchase(Long orderId) {
        final Purchase foundedPurchase = findOrderById(orderId);
        foundedPurchase.archive();
        orderRepository.save(foundedPurchase);
    }
    @Override
    public void deletePurchaseItem(Long orderId, Long itemId) {
        final Purchase foundedPurchase = findOrderById(orderId);
        foundedPurchase.removeOrderItem(itemId);
        orderRepository.save(foundedPurchase);
    }
    @Override
    public void deleteAll() {
        orderRepository.deleteAll();
    }

    private Purchase findOrderById(Long orderId) {
        final var foundedOrderProjection = orderRepository.findById(orderId);
        return new Purchase(foundedOrderProjection);
    }
}
