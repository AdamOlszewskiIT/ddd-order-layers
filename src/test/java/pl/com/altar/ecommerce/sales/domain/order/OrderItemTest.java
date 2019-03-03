package pl.com.altar.ecommerce.sales.domain.order;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.shared.Quantity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class OrderItemTest {

    private static final String ITEM_TEST_NAME = "ITEM_TEST_NAME";

    @Test
    public void shouldCreateOrderItem() {
        //given
        double price = 20D;
        int quantity = 3;
        //when
        OrderItem orderItem = new OrderItem(ITEM_TEST_NAME, price, quantity);
        //then
        assertThat(orderItem).isNotNull();
        assertThat(orderItem.getPrice()).isEqualTo(new Money(price));
        assertThat(orderItem.getQuantity()).isEqualTo(new Quantity(quantity));
    }

    @Test
    public void shouldCalculateTotalValue() {
        //given
        double price = 20D;
        int quantity = 3;
        OrderItem orderItem = new OrderItem(ITEM_TEST_NAME, price, quantity);
        //when
        Money total = orderItem.getTotalPrice();
        //then
        assertThat(total).isNotNull();
        assertThat(total).isEqualTo(new Money(60D));
    }

}
