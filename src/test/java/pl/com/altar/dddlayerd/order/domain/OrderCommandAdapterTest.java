package pl.com.altar.dddlayerd.order.domain;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.altar.dddlayerd.order.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.order.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.order.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.order.client.vm.OrderVM;
import pl.com.altar.dddlayerd.order.domain.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.order.domain.ports.OrderQueryPort;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderCommandAdapterTest {

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
        CreateOrderCommand createOrderCommand = new CreateOrderCommand("test_name");
        //when
        OrderVM orderVM = orderCommandPort.createOrder(createOrderCommand);
        //then
        Long numberOfOrders = orderRepository.count();
        assertThat(numberOfOrders).isOne();
        OrderDetailsVM foundedOrder = orderQueryPort.findOrder(orderVM.getId());
        assertThat(foundedOrder).isNotNull();
        assertThat(foundedOrder.getOrderState()).isEqualTo(OrderState.DRAFT.name());
    }

    @Test
    @Transactional
    public void shouldCreateNewItem() {
        //given
        CreateOrderCommand createOrderCommand = new CreateOrderCommand("test_name");
        AddItemCommand addItemCommand = new AddItemCommand("test_item",123.33, 10);
        //when
        OrderVM createdOrderVM = orderCommandPort.createOrder(createOrderCommand);
        OrderDetailsVM orderDetailsVM = orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        //then
        Long numberOfOrders = orderRepository.count();
        assertThat(numberOfOrders).isOne();
        assertThat(orderDetailsVM).isNotNull();
        assertThat(orderDetailsVM.getOrderState()).isEqualTo(OrderState.DRAFT.name());
        assertThat(orderDetailsVM.getOrderItemVMList().size()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void shouldThrowQuantityIAE() {
        //given
        CreateOrderCommand createOrderCommand = new CreateOrderCommand("test_name");
        AddItemCommand addItemCommand = new AddItemCommand("test_item",123.33, -1);
        //when
        OrderVM createdOrderVM = orderCommandPort.createOrder(createOrderCommand);
        //then - throw IllegalArgumentException
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void shouldThrowMoneyIAE() {
        //given
        CreateOrderCommand createOrderCommand = new CreateOrderCommand("test_name");
        AddItemCommand addItemCommand = new AddItemCommand("test_item",-5.34, 1);
        //when
        OrderVM createdOrderVM = orderCommandPort.createOrder(createOrderCommand);
        //then - throw IllegalArgumentException
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
    }
}