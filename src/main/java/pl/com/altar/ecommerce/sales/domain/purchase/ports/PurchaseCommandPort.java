package pl.com.altar.ecommerce.sales.domain.purchase.ports;


import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;

public interface PurchaseCommandPort {

    PurchaseData addItem(Long orderId, AddItemCommand addItemCommand);

    void deletePurchase(Long orderId);

    void deletePurchaseItem(Long orderId, Long itemId);

    void deleteAll();
}
