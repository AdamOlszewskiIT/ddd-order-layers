package pl.com.altar.ecommerce.sales.domain.purchase.projections;

import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.shared.Quantity;

public interface PurchaseItemData {
    Long getId();
    String getName();
    Quantity getQuantity();
    Money getPrice();
}
