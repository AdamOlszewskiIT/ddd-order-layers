package pl.com.altar.ecommerce.sales.domain.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;

@Getter
@AllArgsConstructor
class Client implements ClientData {

    private Long id;
    private String name;

    public boolean canAfford() { return true;}
}
