package pl.com.altar.dddlayerd.domain;

import lombok.Value;

import java.math.BigDecimal;
import java.util.UUID;

@Value
public class OrderDetails {
    private String name;
    private BigDecimal price;
    private String serialNumber = UUID.randomUUID().toString();
}
