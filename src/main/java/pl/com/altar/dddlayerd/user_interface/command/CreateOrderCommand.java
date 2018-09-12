package pl.com.altar.dddlayerd.user_interface.command;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CreateOrderCommand {
    private String name;
    private BigDecimal price;
}
