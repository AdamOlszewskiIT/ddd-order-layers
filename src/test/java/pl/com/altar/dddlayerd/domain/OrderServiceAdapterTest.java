package pl.com.altar.dddlayerd.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.altar.dddlayerd.domain.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.domain.ports.OrderQueryPort;
import pl.com.altar.dddlayerd.domain.ports.OrderRepository;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceAdapterTest {

    @Autowired
    private OrderQueryPort orderQueryPort;
    @Autowired
    private OrderCommandPort orderCommandPort;
    @Autowired
    private OrderRepository orderRepository;

    @Before
    public void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    public void shouldFindNoOrder() {
        Long numberOfOrders = orderRepository.count();
        assertThat(numberOfOrders).isZero();
    }

    @Test
    @Transactional
    public void shouldFindOneOrderWithStatusCreated() {
        //given
        OrderDetails orderDetails = new OrderDetails("test_name", new BigDecimal(123));
        //when
        orderCommandPort.createOrder(orderDetails);
        //then
        Long numberOfOrders = orderRepository.count();
        assertThat(numberOfOrders).isOne();
        Order foundedOrder = orderQueryPort.findOrder(1L).get();
        assertThat(foundedOrder).isNotNull();
        assertThat(foundedOrder.getOrderState()).isEqualTo(OrderState.CREATED);
    }
}