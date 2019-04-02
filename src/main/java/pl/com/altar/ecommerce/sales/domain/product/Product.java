package pl.com.altar.ecommerce.sales.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.shared.Money;

@Getter
@AllArgsConstructor
class Product implements ProductData {

    private Long id;
    private Money price;
    private String name;
    private ProductType productType;
    private ProductStatus productStatus;

    @Override
    public String getProductTypeName() {
        return this.productType.name();
    }

    @Override
    public String getProductStatusName() {
        return this.productStatus.name();
    }

    public boolean isAvailable() { return productStatus == ProductStatus.AVAILABLE; }

    protected void turnUnavailable() {
        this.productStatus = ProductStatus.UNAVAILABLE;
    }
}
