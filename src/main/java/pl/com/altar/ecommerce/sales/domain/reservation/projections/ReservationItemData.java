package pl.com.altar.ecommerce.sales.domain.reservation.projections;

import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;

public interface ReservationItemData {
    Long getId();
    ProductData getProduct();
    Integer getQuantityValue();
}
