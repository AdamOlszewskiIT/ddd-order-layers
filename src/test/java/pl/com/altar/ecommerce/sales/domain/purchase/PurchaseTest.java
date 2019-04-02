package pl.com.altar.ecommerce.sales.domain.purchase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.command.CreatePurchaseCommand;
import pl.com.altar.ecommerce.shared.Money;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class PurchaseTest {

    private static final String ITEM_TEST_NAME = "ITEM_TEST_NAME";
    private static final String TEST_NAME = "TEST_NAME";

    @Test
    public void shouldCreateOrder() {
        //given
        CreatePurchaseCommand command = new CreatePurchaseCommand(TEST_NAME);
        //when
        Purchase createdPurchase = new Purchase(command);
        //then
        assertThat(createdPurchase).isNotNull();
        assertThat(createdPurchase.getName()).isEqualTo(TEST_NAME);
    }

    @Test
    public void shouldAddItemsAndCalculatePrice() {
        //given
        CreatePurchaseCommand command = new CreatePurchaseCommand(TEST_NAME);
        Purchase createdPurchase = new Purchase(command);
        AddItemCommand addFirstItemCommand = new AddItemCommand(ITEM_TEST_NAME,10,2);
        AddItemCommand addSecondItemCommand = new AddItemCommand(ITEM_TEST_NAME,20,3);
        AddItemCommand addThirdItemCommand = new AddItemCommand(ITEM_TEST_NAME,30,4);
        //when
        createdPurchase.addItem(addFirstItemCommand);
        createdPurchase.addItem(addSecondItemCommand);
        createdPurchase.addItem(addThirdItemCommand);
        //then
        assertThat(createdPurchase.getPurchaseItems()).isNotNull();
        assertThat(createdPurchase.getPurchaseItems().size()).isEqualTo(3);
        assertThat(createdPurchase.getTotalCost()).isEqualTo(new Money(200D));
    }

}
