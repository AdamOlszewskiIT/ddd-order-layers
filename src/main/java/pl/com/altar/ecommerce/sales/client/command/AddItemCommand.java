package pl.com.altar.ecommerce.sales.client.command;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Value
@Validated
@AllArgsConstructor
public class AddItemCommand {
    @NotNull(message = "Name should not be empty")
    private String name;
    @DecimalMin(value = "0.0", message = "Price should be grater than zero")
    private double price;
    @NotNull
    @Min(value = 1, message = "You have to add at least one item")
    private Integer quantity;

    @SuppressWarnings("unused")
    /* Jackson serialization purpose only */
    public AddItemCommand() {
        this("default", 0D, 1);
    }
}
