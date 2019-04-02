package pl.com.altar.ecommerce.sales.domain.purchase.ports;

import pl.com.altar.ecommerce.sales.client.command.CreatePurchaseCommand;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;

public interface PurchaseFactoryPort {

    PurchaseData createPurchase(CreatePurchaseCommand createPurchaseCommand);

}
