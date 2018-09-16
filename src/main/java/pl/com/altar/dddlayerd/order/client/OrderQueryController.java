package pl.com.altar.dddlayerd.order.client;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.domain.ports.OrderQueryPort;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderQueryController {

    private final OrderQueryPort orderQueryPort;

    @GetMapping
    public ResponseEntity<OrderDetailsVM> getOrder(@RequestParam("orderId") Long orderId) {
        val orderVM = orderQueryPort.findOrder(orderId);
        return new ResponseEntity<>(orderVM, HttpStatus.OK);
    }

}
