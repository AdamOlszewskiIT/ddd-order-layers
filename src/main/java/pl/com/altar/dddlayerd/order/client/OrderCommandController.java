package pl.com.altar.dddlayerd.order.client;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.altar.dddlayerd.order.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.order.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;
import pl.com.altar.dddlayerd.order.domain.ports.OrderCommandPort;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderCommandController {

    private final OrderCommandPort orderCommandPort;

    @PostMapping
    public ResponseEntity<OrderVM> createOrder(@Valid @RequestBody CreateOrderCommand createOrderCommand) {
        val orderVM = orderCommandPort.createOrder(createOrderCommand);
        return new ResponseEntity<>(orderVM, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderDetailsVM> addItemToOrder(@PathVariable Long orderId, @Valid @RequestBody AddItemCommand addItemCommand) {
        val orderDetailsVM = orderCommandPort.addItem(orderId, addItemCommand);
        return new ResponseEntity<>(orderDetailsVM, HttpStatus.CREATED);
    }


}
