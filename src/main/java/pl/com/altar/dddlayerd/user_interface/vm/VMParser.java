package pl.com.altar.dddlayerd.user_interface.vm;

import pl.com.altar.dddlayerd.domain.Order;
import pl.com.altar.dddlayerd.domain.OrderDetails;

public class VMParser {

    public static OrderVM parse(Order order) {
        OrderDetails orderDetails = order.getOrderDetails();
        return new OrderVM(
                order.getId().toString(),
                order.getOrderState().name(),
                orderDetails.getName(),
                orderDetails.getPrice().toEngineeringString(),
                orderDetails.getSerialNumber()
        );
    }

}
