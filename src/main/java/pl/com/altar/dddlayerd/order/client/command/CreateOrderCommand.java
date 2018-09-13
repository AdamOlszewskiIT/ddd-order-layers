package pl.com.altar.dddlayerd.order.client.command;

import lombok.NonNull;
import lombok.Value;


@Value
public class CreateOrderCommand {
    @NonNull
    private String name;
}
