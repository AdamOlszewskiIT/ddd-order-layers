package pl.com.altar.ecommerce.sales.domain.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseItemData;
import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.shared.Quantity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
class PurchaseItem implements PurchaseItemData {

    private Long id;

    private String name;

    private Quantity quantity;

    private Money price = Money.zero();

    private PurchaseItem(String name, Quantity quantity, Money price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    PurchaseItem(String name, double price, Integer quantity) {
        this(name, new Quantity(quantity), new Money(price));
    }

    protected Money getTotalPrice() {
        return this.price.multiplyBy(this.quantity.getNumberOfElements());
    }

}
