package pl.com.altar.ecommerce.sales.application;

import lombok.AllArgsConstructor;
import pl.com.altar.ecommerce.sales.application.ports.OrderingServicePort;
import pl.com.altar.ecommerce.sales.client.command.AddProductToOrderCommand;
import pl.com.altar.ecommerce.sales.domain.client.ports.ClientRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;
import pl.com.altar.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.altar.ecommerce.sales.domain.product.ports.ProductRepositoryPort;
import pl.com.altar.ecommerce.sales.domain.product.projections.ProductData;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseCommandPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseFactoryPort;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationCommandPort;
import pl.com.altar.ecommerce.sales.domain.reservation.ports.ReservationFactoryPort;
import pl.com.altar.ecommerce.sales.domain.reservation.projections.ReservationData;
import pl.com.altar.ecommerce.shared.Quantity;
import pl.com.altar.ecommerce.shared.annotations.application.ApplicationService;
import pl.com.altar.ecommerce.system.SystemContext;

@ApplicationService
@AllArgsConstructor
public class OrderingService implements OrderingServicePort {

    private final ClientRepositoryPort clientRepositoryPort;
    private final PurchaseCommandPort purchaseCommandPort;
    private final PurchaseFactoryPort purchaseFactoryPort;
    private final SuggestionService suggestionService;
    private final ProductRepositoryPort productRepositoryPort;
    private final ReservationFactoryPort reservationFactoryPort;
    private final ReservationCommandPort reservationCommandPort;
    private final SystemContext systemContext;

    @Override
    public Long createOrder() {
        ReservationData reservation = reservationFactoryPort.create(loadClient());
        return reservation.getId();
    }

    @Override
    public void addProduct(AddProductToOrderCommand command) {
        Quantity quantity = new Quantity(command.getQuantity());
        ProductData product = productRepositoryPort.findById(command.getProductId());
        if (!product.isAvailable()) {
            ClientData client = loadClient();
            product = suggestionService.suggestEquivalent(product, client);
        }
        reservationCommandPort.addProductToReservation(command.getOrderId(), product, quantity);
    }

    private ClientData loadClient() {
        return clientRepositoryPort.findById(systemContext.getSystemUser().getClientId());
    }

}
