package pl.com.altar.ecommerce.sales.domain.purchase.projections;

import pl.com.altar.ecommerce.shared.Money;

import java.sql.Timestamp;
import java.util.List;

public interface PurchaseData {
    Long getId();
    String getName();
    Money getTotalCost();
    String getSerialNumber();
    Timestamp getSubmitDate();
    String getOrderStateName();
    List<PurchaseItemData> getOrderItemProjections();
}
