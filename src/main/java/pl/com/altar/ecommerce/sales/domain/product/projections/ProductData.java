package pl.com.altar.ecommerce.sales.domain.product.projections;

import pl.com.altar.ecommerce.shared.Money;

public interface ProductData {
    Long getId();
    Money getPrice();
    String getName();
    String getProductTypeName();
    String getProductStatusName();
    boolean isAvailable();
}
