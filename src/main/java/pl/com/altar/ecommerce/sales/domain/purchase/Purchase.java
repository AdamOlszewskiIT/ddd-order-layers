package pl.com.altar.ecommerce.sales.domain.purchase;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.command.CreatePurchaseCommand;
import pl.com.altar.ecommerce.sales.client.exception.ResourceNotFoundException;
import pl.com.altar.ecommerce.sales.domain.purchase.exceptions.OrderOperationException;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseItemData;
import pl.com.altar.ecommerce.shared.Money;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.com.altar.ecommerce.sales.domain.SalesMessages.OPERATION_ALLOWED_ONLY_IN_DRAFT_STATUS;
import static pl.com.altar.ecommerce.sales.domain.purchase.PurchaseState.*;

@Getter
@AllArgsConstructor
class Purchase implements PurchaseData {

    private Long id;
    private String name;
    private Money totalCost;
    private String serialNumber = UUID.randomUUID().toString();
    private Timestamp submitDate;
    private PurchaseState purchaseState;
    private List<PurchaseItem> purchaseItems = new ArrayList<>();

    private Purchase(String name, Money totalCost, PurchaseState purchaseState) {
        this.name = name;
        this.totalCost = totalCost;
        this.purchaseState = purchaseState;
    }

    Purchase(CreatePurchaseCommand command) {
        this(command.getName(), Money.zero(), DRAFT);
    }

    protected PurchaseItem addItem(AddItemCommand command) {
        checkIfDraft();
        final var newItem = new PurchaseItem(
                command.getName(),
                command.getPrice(),
                command.getQuantity()
        );
        this.purchaseItems.add(newItem);
        recalculatePrice();
        return newItem;
    }

    protected void submit() {
        checkIfDraft();
        this.purchaseState = SUBMITTED;
        this.submitDate = new Timestamp(System.currentTimeMillis());
    }

    protected void archive() {
        this.purchaseState = ARCHIVED;
    }

    private void checkIfDraft() {
        if (purchaseState != DRAFT) {
            throw new OrderOperationException(OPERATION_ALLOWED_ONLY_IN_DRAFT_STATUS);
        }
    }

    private void recalculatePrice() {
        this.totalCost = Money.zero();
        purchaseItems.forEach(item -> this.totalCost = this.totalCost.add(item.getTotalPrice()));
    }

    protected void clearOrderItems() {
        this.purchaseItems.clear();
        recalculatePrice();
    }

    protected void removeOrderItem(Long itemId) {
        final var numberOfItemsBeforeDeletion = this.getPurchaseItems().size();
        this.getPurchaseItems()
                .removeIf(purchaseItem -> Objects.equals(purchaseItem.getId(), itemId));
        if (numberOfItemsBeforeDeletion == this.getPurchaseItems().size()) {
            throw new ResourceNotFoundException(PurchaseItem.class.getName(), itemId);
        }
        recalculatePrice();
    }

    private static PurchaseItem of(PurchaseItemData oip) {
        return new PurchaseItem(
                oip.getId(),
                oip.getName(),
                oip.getQuantity(),
                oip.getPrice()
        );
    }

    Purchase(PurchaseData op) {
        this(
                op.getId(),
                op.getName(),
                op.getTotalCost(),
                op.getSerialNumber(),
                op.getSubmitDate(),
                valueOf(op.getOrderStateName()),
                null
        );
        this.purchaseItems = parse(op.getOrderItemProjections());
    }

    private static List<PurchaseItem> parse(List<PurchaseItemData> purchaseItemData) {
        return purchaseItemData
                .stream()
                .map(Purchase::of)
                .collect(Collectors.toList());
    }

    @Override
    public String getOrderStateName() {
        return this.purchaseState.name();
    }

    @Override
    public List<PurchaseItemData> getOrderItemProjections() {
        return new ArrayList<>(this.purchaseItems);
    }
}
