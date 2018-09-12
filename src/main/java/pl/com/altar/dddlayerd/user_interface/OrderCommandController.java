package pl.com.altar.dddlayerd.user_interface;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.altar.dddlayerd.domain.OrderDetails;
import pl.com.altar.dddlayerd.domain.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.user_interface.command.CreateOrderCommand;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderCommandController {

    private final OrderCommandPort orderCommandPort;

    @PostMapping
    public HttpStatus createOrder(CreateOrderCommand createOrderCommand) {
        OrderDetails orderDetails = new OrderDetails(createOrderCommand.getName(), createOrderCommand.getPrice());
        orderCommandPort.createOrder(orderDetails);
        return HttpStatus.CREATED;
    }


}
