package pl.com.altar.ecommerce.sales.domain.purchase.events;


import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.shared.annotations.event.Event;

import java.io.Serializable;

@Event
@Getter
@AllArgsConstructor
@SuppressWarnings("serial")
public class PurchaseCreated implements Serializable {
    private PurchaseData purchaseData;
    private ClientData clientData;
}
