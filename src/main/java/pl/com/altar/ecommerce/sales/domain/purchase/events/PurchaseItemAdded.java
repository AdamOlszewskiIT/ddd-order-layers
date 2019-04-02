package pl.com.altar.ecommerce.sales.domain.purchase.events;


import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseItemData;
import pl.com.altar.ecommerce.shared.annotations.event.Event;

import java.io.Serializable;

@Event
@Getter
@AllArgsConstructor
@SuppressWarnings("serial")
public class PurchaseItemAdded implements Serializable {
    private Long purchaseId;
    private PurchaseItemData purchaseItemData;
}
