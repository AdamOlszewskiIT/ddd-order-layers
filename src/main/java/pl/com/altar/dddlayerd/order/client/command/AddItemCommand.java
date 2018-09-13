package pl.com.altar.dddlayerd.order.client.command;

import lombok.NonNull;
import lombok.Value;

@Value
public class AddItemCommand {
    @NonNull
    private String name;
    double price;
    @NonNull
    Integer quantity;
}
