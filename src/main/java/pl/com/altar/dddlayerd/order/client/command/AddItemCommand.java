package pl.com.altar.dddlayerd.order.client.command;

import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
@Validated
public class AddItemCommand {
    @NotNull(message = "Name should not be empty")
    private String name;
    @DecimalMin(value = "0.0", message = "Price should be grater than zero")
    double price;
    @NotNull
    @Min(value = 1, message = "You have to add at least one item")
    Integer quantity;
}
