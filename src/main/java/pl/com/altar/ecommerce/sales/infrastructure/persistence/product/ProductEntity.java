package pl.com.altar.ecommerce.sales.infrastructure.persistence.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.shared.Money;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "product_table")
@AllArgsConstructor
public class ProductEntity implements ProductData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id")
    private Long id;
    @Embedded
    private Money price;
    private String name;
    private String productTypeName;
    private String productStatusName;

    public ProductEntity(ProductData productData) {
        this(
                productData.getId(),
                productData.getPrice(),
                productData.getName(),
                productData.getProductTypeName(),
                productData.getProductStatusName()
        );
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}
