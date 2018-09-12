package pl.com.altar.dddlayerd.user_interface;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.com.altar.dddlayerd.domain.Order;
import pl.com.altar.dddlayerd.domain.ports.OrderQueryPort;
import pl.com.altar.dddlayerd.user_interface.client_exception.OderNotFoundException;
import pl.com.altar.dddlayerd.user_interface.vm.OrderVM;

import java.util.Optional;

import static pl.com.altar.dddlayerd.user_interface.vm.VMParser.parse;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderQueryController {

    private final OrderQueryPort orderQueryPort;

    @GetMapping
    public ResponseEntity<OrderVM> getOrder(@RequestParam("orderId") Long orderId) {
        Optional<Order> orderOptional = orderQueryPort.findOrder(orderId);
        return new ResponseEntity<>(parse(orderOptional.orElseThrow(() -> new OderNotFoundException(orderId))), HttpStatus.OK);
    }




}
