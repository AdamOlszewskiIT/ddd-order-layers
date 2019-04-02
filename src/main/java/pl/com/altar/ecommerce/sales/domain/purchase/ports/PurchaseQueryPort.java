package pl.com.altar.ecommerce.sales.domain.purchase.ports;


import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;

import java.util.List;

public interface PurchaseQueryPort {

    PurchaseData findPurchase(Long orderId);

    List<PurchaseData> findAll();

    List<PurchaseData> findAllWithDetails();

}
