package pl.com.altar.dddlayerd.user_interface.vm;

import lombok.Value;

@Value
public class OrderVM {
    private String id;
    private String orderState;
    private String name;
    private String price;
    private String serialNumber;
}
