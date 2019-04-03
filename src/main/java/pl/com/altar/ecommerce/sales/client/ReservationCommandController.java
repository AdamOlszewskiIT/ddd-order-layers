package pl.com.altar.ecommerce.sales.client;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.altar.ecommerce.sales.application.OrderingService;
import pl.com.altar.ecommerce.sales.client.command.AddProductToOrderCommand;

@RestController
@RequestMapping("/reservations")
@AllArgsConstructor
public class ReservationCommandController {

    private final OrderingService orderingService;

    @PostMapping
    public ResponseEntity<Long> createReservation() {
        return new ResponseEntity<>(orderingService.createOrder(), HttpStatus.CREATED);
    }

    @PostMapping("/products")
    public HttpStatus createAddProductToReservation(@RequestBody AddProductToOrderCommand addProductToOrderCommand) {
        orderingService.addProduct(addProductToOrderCommand);
        return HttpStatus.CREATED;
    }

}
