package pl.com.altar.ecommerce.sales.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.altar.ecommerce.sales.domain.order.OrderCommandAdapter;
import pl.com.altar.ecommerce.sales.domain.order.OrderFactoryAdapter;
import pl.com.altar.ecommerce.sales.domain.order.OrderQueryAdapter;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderCommandPort;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderEventPublisher;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderFactoryPort;
import pl.com.altar.ecommerce.sales.domain.order.ports.OrderQueryPort;
import pl.com.altar.ecommerce.sales.infrastructure.repository.OrderRepositoryAdapter;

@Configuration
public class OrderConfiguration {

    @Bean
    public OrderCommandPort getOrderCommand(OrderRepositoryAdapter orderRepositoryAdapter, OrderEventPublisher orderEventPublisher) {
        return new OrderCommandAdapter(orderRepositoryAdapter, orderEventPublisher);
    }

    @Bean
    public OrderFactoryPort getOrderFactory(OrderRepositoryAdapter orderRepositoryAdapter, OrderEventPublisher orderEventPublisher) {
        return new OrderFactoryAdapter(orderRepositoryAdapter, orderEventPublisher);
    }

    @Bean
    public OrderQueryPort getOrderQuery(OrderRepositoryAdapter orderRepositoryAdapter) {
        return new OrderQueryAdapter(orderRepositoryAdapter);
    }


}
