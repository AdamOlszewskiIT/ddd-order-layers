package pl.com.altar.dddlayerd.order.client.command;

import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;


@Validated
@Value
public class CreateOrderCommand {
    @NotNull
    private String name;
}
