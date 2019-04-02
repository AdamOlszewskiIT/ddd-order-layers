package pl.com.altar.ecommerce.sales.infrastructure.persistence.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.com.altar.ecommerce.sales.domain.client.projections.ClientData;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Data
@Table(name = "client_table")
@AllArgsConstructor
public class ClientEntity implements ClientData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "client_id")
    private Long id;
    private String name;

    public ClientEntity(ClientData clientData) {
        this(clientData.getId(), clientData.getName());
    }

    @Override
    public boolean canAfford() {
        return true;
    }
}
