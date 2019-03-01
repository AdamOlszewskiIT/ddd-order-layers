package pl.com.altar.dddlayerd.domain.order;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.altar.dddlayerd.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.client.vm.OrderDetailsVM;
import pl.com.altar.dddlayerd.client.vm.OrderVM;
import pl.com.altar.dddlayerd.domain.order.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderFactoryPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderQueryPort;
import pl.com.altar.dddlayerd.infrastructure.repository.OrderRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderCommandAdapterTest {

    private static final String TEST_NAME = "test_name";
    @Autowired
    private OrderQueryPort orderQueryPort;
    @Autowired
    private OrderCommandPort orderCommandPort;
    @Autowired
    private OrderFactoryPort orderFactoryPort;
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
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(TEST_NAME);
        //when
        OrderVM orderVM = orderFactoryPort.createOrder(createOrderCommand);
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
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item",123.33, 10);
        //when
        OrderVM createdOrderVM = orderFactoryPort.createOrder(createOrderCommand);
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
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item",123.33, -1);
        //when
        OrderVM createdOrderVM = orderFactoryPort.createOrder(createOrderCommand);
        //then - throw IllegalArgumentException
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void shouldThrowMoneyIAE() {
        //given
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item",-5.34, 1);
        //when
        OrderVM createdOrderVM = orderFactoryPort.createOrder(createOrderCommand);
        //then - throw IllegalArgumentException
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
    }

    @Test
    @Transactional
    public void shouldDeleteOneItemFromOrder() {
        //given
        CreateOrderCommand createOrderCommand = new CreateOrderCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item",5.34, 1);
        OrderVM createdOrderVM = orderFactoryPort.createOrder(createOrderCommand);
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        orderCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        final var orderItemsBeforeDeletion = orderQueryPort.findOrder(createdOrderVM.getId()).getOrderItemVMList();
        final var indexOfFirstItemToRemove = 0;
        final var indexOfSecondItemToRemove = 4;
        final var firstItemToRemove = orderItemsBeforeDeletion.get(indexOfFirstItemToRemove);
        final var secondItemToRemove = orderItemsBeforeDeletion.get(indexOfSecondItemToRemove);
        //when
        orderCommandPort.deleteOrderItem(createdOrderVM.getId(), firstItemToRemove.getId());
        orderCommandPort.deleteOrderItem(createdOrderVM.getId(), secondItemToRemove.getId());
        //then
        final var orderItemsAfterDeletion = orderQueryPort.findOrder(createdOrderVM.getId()).getOrderItemVMList();
        assertThat(orderItemsBeforeDeletion.size()).isGreaterThan(orderItemsAfterDeletion.size());
        assertThat(orderItemsAfterDeletion.contains(firstItemToRemove)).isFalse();
        assertThat(orderItemsAfterDeletion.contains(secondItemToRemove)).isFalse();

    }
}
