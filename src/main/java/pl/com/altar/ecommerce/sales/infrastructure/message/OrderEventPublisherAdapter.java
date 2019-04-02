package pl.com.altar.ecommerce.sales.infrastructure.message;

import org.springframework.stereotype.Service;
import pl.com.altar.ecommerce.shared.support.DomainEventPublisher;

import java.io.Serializable;


@Service
public class OrderEventPublisherAdapter implements DomainEventPublisher {
    @Override
    public void publish(Serializable event) {
        System.out.println("Publishing event ...  ");
    }
}
