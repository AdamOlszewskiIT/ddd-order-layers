package pl.com.altar.ecommerce.sales.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.com.altar.ecommerce.sales.client.vm.OrderDetailsVM;
import pl.com.altar.ecommerce.sales.client.vm.OrderVM;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderQueryPort;

import java.util.List;


@RestController
@RequestMapping("/orders")
@AllArgsConstructor
public class OrderQueryController {

    private final OrderQueryPort orderQueryPort;


    @GetMapping
    public ResponseEntity<List<OrderVM>> getAllOrders() {
        final var orderList = orderQueryPort.findAll();
        return new ResponseEntity<>(ModelMapper.map(orderList), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetailsVM> getOrder(@PathVariable Long orderId) {
        final var orderVM = orderQueryPort.findOrder(orderId);
        return new ResponseEntity<>(ModelMapper.mapDetails(orderVM), HttpStatus.OK);
    }

}
