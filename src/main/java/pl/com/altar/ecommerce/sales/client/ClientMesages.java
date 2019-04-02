package pl.com.altar.ecommerce.sales.client;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ClientMesages {
    public static final String ORDER_ID_SHOULD_NOT_BE_EMPTY = "'orderId' should not be empty";
    public static final String PRODUCT_ID_SHOULD_NOT_BE_EMPTY = "'productId' should not be empty";
    public static final String YOU_HAVE_TO_ADD_AT_LEAST_ONE_ITEM_TO_ORDER = "You have to add at least one item to order";
}
