package pl.com.altar.ecommerce.sales.infrastructure.persistence.purchase;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseItemData;
import pl.com.altar.ecommerce.shared.Money;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NoArgsConstructor
@Data
@Table(name = "order_table")
@AllArgsConstructor
public class PurchaseEntity implements PurchaseData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    private String name;

    @Embedded
    private Money totalCost = Money.zero();

    private String serialNumber = UUID.randomUUID().toString();

    private Timestamp submitDate;

    private String orderState;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PurchaseItemEntity> orderItems = new ArrayList<>();

    protected static PurchaseItemEntity of(PurchaseItemData oip, PurchaseEntity oe) {
        return new PurchaseItemEntity(
                oip.getId(),
                oip.getName(),
                oip.getQuantity(),
                oip.getPrice(),
                oe
        );
    }

    public PurchaseEntity(PurchaseData op) {
        this(
                op.getId(),
                op.getName(),
                op.getTotalCost(),
                op.getSerialNumber(),
                op.getSubmitDate(),
                op.getOrderStateName(),
                null
                );
        this.orderItems = parse(op.getOrderItemProjections(), this);
    }

    private static List<PurchaseItemEntity> parse(List<PurchaseItemData> purchaseItemData, PurchaseEntity oe) {
        return purchaseItemData
                .stream()
                .map(op -> of(op, oe))
                .collect(Collectors.toList());
    }


    @Override
    public String getOrderStateName() {
        return this.orderState;
    }

    @Override
    public List<PurchaseItemData> getOrderItemProjections() {
        return new ArrayList<>(this.orderItems);
    }
}
