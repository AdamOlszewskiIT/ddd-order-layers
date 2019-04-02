package pl.com.altar.ecommerce.sales.domain.purchase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.altar.ecommerce.shared.Money;
import pl.com.altar.ecommerce.shared.Quantity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class PurchaseItemTest {

    private static final String ITEM_TEST_NAME = "ITEM_TEST_NAME";

    @Test
    public void shouldCreateOrderItem() {
        //given
        double price = 20D;
        int quantity = 3;
        //when
        PurchaseItem purchaseItem = new PurchaseItem(ITEM_TEST_NAME, price, quantity);
        //then
        assertThat(purchaseItem).isNotNull();
        assertThat(purchaseItem.getPrice()).isEqualTo(new Money(price));
        assertThat(purchaseItem.getQuantity()).isEqualTo(new Quantity(quantity));
    }

    @Test
    public void shouldCalculateTotalValue() {
        //given
        double price = 20D;
        int quantity = 3;
        PurchaseItem purchaseItem = new PurchaseItem(ITEM_TEST_NAME, price, quantity);
        //when
        Money total = purchaseItem.getTotalPrice();
        //then
        assertThat(total).isNotNull();
        assertThat(total).isEqualTo(new Money(60D));
    }

}
