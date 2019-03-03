package pl.com.altar.ecommerce.sales.domain.order;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.command.CreateOrderCommand;
import pl.com.altar.ecommerce.shared.Money;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class OrderTest {

    private static final String ITEM_TEST_NAME = "ITEM_TEST_NAME";
    private static final String TEST_NAME = "TEST_NAME";

    @Test
    public void shouldCreateOrder() {
        //given
        CreateOrderCommand command = new CreateOrderCommand(TEST_NAME);
        //when
        Order createdOrder = new Order(command);
        //then
        assertThat(createdOrder).isNotNull();
        assertThat(createdOrder.getName()).isEqualTo(TEST_NAME);
    }

    @Test
    public void shouldAddItemsAndCalculatePrice() {
        //given
        CreateOrderCommand command = new CreateOrderCommand(TEST_NAME);
        Order createdOrder = new Order(command);
        AddItemCommand addFirstItemCommand = new AddItemCommand(ITEM_TEST_NAME,10,2);
        AddItemCommand addSecondItemCommand = new AddItemCommand(ITEM_TEST_NAME,20,3);
        AddItemCommand addThirdItemCommand = new AddItemCommand(ITEM_TEST_NAME,30,4);
        //when
        createdOrder.addItem(addFirstItemCommand);
        createdOrder.addItem(addSecondItemCommand);
        createdOrder.addItem(addThirdItemCommand);
        //then
        assertThat(createdOrder.getOrderItems()).isNotNull();
        assertThat(createdOrder.getOrderItems().size()).isEqualTo(3);
        assertThat(createdOrder.getPrice()).isEqualTo(new Money(200D));
    }

}
