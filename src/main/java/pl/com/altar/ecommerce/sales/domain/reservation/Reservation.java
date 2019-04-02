package pl.com.altar.ecommerce.sales.domain.reservation;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;
import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationData;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationItemData;
import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.shared.Quantity;
import pl.com.altar.ecommerce.shared.annotations.domain.AggregateRoot;
import pl.com.altar.ecommerce.shared.exceptions.DomainOperationException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static pl.com.altar.ecommerce.sales.domain.SalesMessages.PRODUCT_IS_NO_LONGER_AVAILABLE;
import static pl.com.altar.ecommerce.sales.domain.SalesMessages.RESERVATION_ALREADY_CLOSED;

@Getter
@AggregateRoot
@AllArgsConstructor
class Reservation implements ReservationData {
    private Long id;
    private ReservationStatus status;
    private List<ReservationItem> items;
    private ClientData clientData;
    private Date createDate;

    protected Reservation(ReservationData reservationData) {
        this(
                reservationData.getId(),
                ReservationStatus.valueOf(reservationData.getStatusName()),
                null,
                reservationData.getClientData(),
                reservationData.getCreateDate()
        );
        this.items = parse(reservationData.getItemsProjections());
    }
    protected void add(ProductData productData, Quantity quantity) {
        checkIfProductIsNotClosed(isClosed());
        checkIfProductIsAvailable(productData.isAvailable());
        addNewOrIncreaseProductQuantity(productData, quantity);
    }
    protected boolean isClosed() {
        return status.equals(ReservationStatus.CLOSED);
    }
    protected boolean contains(ProductData product) {
        final var numberOfItems = items.stream()
                .filter(item -> item.getProduct().equals(product))
                .count();
        return numberOfItems != 0;
    }
    protected void close(){
        if (isClosed()) {
            throw new DomainOperationException(id, RESERVATION_ALREADY_CLOSED);
        }
        status = ReservationStatus.CLOSED;
    }

    private void addNewOrIncreaseProductQuantity(ProductData productData, Quantity quantity) {
        if(contains(productData)) {
            increase(productData, quantity);
        } else {
            addNew(productData, quantity);
        }
    }
    private void checkIfProductIsAvailable(boolean isAvailable) {
        if (!isAvailable) {
            throw new DomainOperationException(id, PRODUCT_IS_NO_LONGER_AVAILABLE);
        }
    }
    private void checkIfProductIsNotClosed(boolean closed) {
        if (closed) {
            throw new DomainOperationException(id, RESERVATION_ALREADY_CLOSED);
        }
    }
    private void addNew(ProductData product, Quantity quantity) {
        ReservationItem item = new ReservationItem(null, product, quantity);
        items.add(item);
    }
    private void increase(ProductData product, Quantity quantity) {
        items.forEach(item -> {
            if(item.getProduct().equals(product)) {
                item.changeQuantity(quantity);
            }
        });
    }
    private List<ReservationItem> parse(List<ReservationItemData> reservationItemData) {
        return reservationItemData.stream()
                .map(ReservationItem::new)
                .collect(Collectors.toList());
    }
    @Override
    public String getStatusName() {
        return this.status.name();
    }
    @Override
    public List<ReservationItemData> getItemsProjections() {
        return new ArrayList<>(this.items);
    }
}
