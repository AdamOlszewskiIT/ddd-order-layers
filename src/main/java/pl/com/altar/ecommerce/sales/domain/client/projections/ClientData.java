package pl.com.altar.ecommerce.sales.domain.client.projections;

public interface ClientData {
    Long getId();
    String getName();
    boolean canAfford();
}
