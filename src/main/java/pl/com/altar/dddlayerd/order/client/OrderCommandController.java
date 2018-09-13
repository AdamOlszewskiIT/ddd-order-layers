package pl.com.altar.dddlayerd.order.client;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.altar.dddlayerd.order.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;
import pl.com.altar.dddlayerd.order.domain.ports.OrderCommandPort;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderCommandController {

    private final OrderCommandPort orderCommandPort;

    @PostMapping
    public ResponseEntity<OrderVM> createOrder(CreateOrderCommand createOrderCommand) {
        val orderVM = orderCommandPort.createOrder(createOrderCommand);
        return new ResponseEntity<>(orderVM, HttpStatus.CREATED);
    }


}
