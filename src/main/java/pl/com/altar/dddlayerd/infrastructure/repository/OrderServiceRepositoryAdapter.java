package pl.com.altar.dddlayerd.infrastructure.repository;

import org.springframework.data.repository.Repository;
import pl.com.altar.dddlayerd.domain.Order;
import pl.com.altar.dddlayerd.domain.ports.OrderRepository;

public interface OrderServiceRepositoryAdapter extends Repository<Order, Long>, OrderRepository {
}
