package pl.com.altar.dddlayerd.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.altar.dddlayerd.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.client.vm.OrderVM;
import pl.com.altar.dddlayerd.domain.order.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderFactoryPort;

import javax.validation.Valid;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderCommandController {

    private final OrderCommandPort orderCommandPort;
    private final OrderFactoryPort orderFactoryPort;

    @PostMapping
    public ResponseEntity<OrderVM> createOrder(@Valid @RequestBody CreateOrderCommand createOrderCommand) {
        final var orderVM = orderFactoryPort.createOrder(createOrderCommand);
        return new ResponseEntity<>(orderVM, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/items")
    public ResponseEntity<OrderDetailsVM> addItemToOrder(@PathVariable Long orderId, @Valid @RequestBody AddItemCommand addItemCommand) {
        final var orderDetailsVM = orderCommandPort.addItem(orderId, addItemCommand);
        return new ResponseEntity<>(orderDetailsVM, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}/items/{itemId}")
    public void deleteItem(@PathVariable Long orderId, @PathVariable Long itemId) {
        //final var
    }
}
