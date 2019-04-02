package pl.com.altar.ecommerce.sales.client.command;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static pl.com.altar.ecommerce.sales.client.ClientMesages.*;

@Value
@Validated
@AllArgsConstructor
public class AddProductToOrderCommand {

    @NotNull(message = ORDER_ID_SHOULD_NOT_BE_EMPTY)
    private Long orderId;
    @NotNull(message = PRODUCT_ID_SHOULD_NOT_BE_EMPTY)
    private Long productId;
    @Min(value = 1, message = YOU_HAVE_TO_ADD_AT_LEAST_ONE_ITEM_TO_ORDER)
    private Integer quantity;

    @SuppressWarnings("unused")
    /* Jackson serialization purpose only */
    public AddProductToOrderCommand() {
        this(-1L, -1L, 1);
    }
}
