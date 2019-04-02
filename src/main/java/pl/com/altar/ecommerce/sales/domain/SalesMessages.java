package pl.com.altar.ecommerce.sales.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SalesMessages {
    public static final String RESERVATION_ALREADY_CLOSED = "Reservation already closed";
    public static final String PRODUCT_IS_NO_LONGER_AVAILABLE = "Product is no longer available";
    public static final String OPERATION_ALLOWED_ONLY_IN_DRAFT_STATUS = "Operation allowed only in DRAFT status";
    public static final String CLIENT_CAN_NOT_CREATE_RESERVATIONS = "Client can not create reservations";

}
