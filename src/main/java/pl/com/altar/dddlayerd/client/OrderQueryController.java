package pl.com.altar.dddlayerd.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.domain.order.ports.OrderQueryPort;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderQueryController {

    private final OrderQueryPort orderQueryPort;

    @GetMapping
    public ResponseEntity<OrderDetailsVM> getOrder(@RequestParam("orderId") Long orderId) {
        final var orderVM = orderQueryPort.findOrder(orderId);
        return new ResponseEntity<>(orderVM, HttpStatus.OK);
    }

}
