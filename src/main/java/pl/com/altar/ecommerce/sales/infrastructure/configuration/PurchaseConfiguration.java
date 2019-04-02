package pl.com.altar.ecommerce.sales.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.altar.ecommerce.sales.domain.purchase.PurchaseCommandAdapter;
import pl.com.altar.ecommerce.sales.domain.purchase.PurchaseFactoryAdapter;
import pl.com.altar.ecommerce.sales.domain.purchase.PurchaseQueryAdapter;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseCommandPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseFactoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseQueryPort;
import pl.com.altar.ecommerce.sales.infrastructure.persistence.purchase.PurchaseRepositoryAdapter;
import pl.com.altar.ecommerce.shared.support.DomainEventPublisher;

@Configuration
public class PurchaseConfiguration {

    @Bean
    public PurchaseCommandPort getPurchaseCommand(PurchaseRepositoryAdapter orderRepositoryAdapter, DomainEventPublisher domainEventPublisher) {
        return new PurchaseCommandAdapter(orderRepositoryAdapter, domainEventPublisher);
    }

    @Bean
    public PurchaseFactoryPort getPurchaseFactory(PurchaseRepositoryAdapter orderRepositoryAdapter, DomainEventPublisher domainEventPublisher) {
        return new PurchaseFactoryAdapter(orderRepositoryAdapter, domainEventPublisher);
    }

    @Bean
    public PurchaseQueryPort getPurchaseQuery(PurchaseRepositoryAdapter orderRepositoryAdapter) {
        return new PurchaseQueryAdapter(orderRepositoryAdapter);
    }


}
