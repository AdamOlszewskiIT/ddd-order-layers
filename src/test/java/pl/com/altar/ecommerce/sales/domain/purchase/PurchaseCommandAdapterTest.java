package pl.com.altar.ecommerce.sales.domain.purchase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.command.CreatePurchaseCommand;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseCommandPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseFactoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseQueryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;
import pl.com.altar.ecommerce.sales.infrastructure.persistence.purchase.PurchaseRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseCommandAdapterTest {

    private static final String TEST_NAME = "test_name";
    @Autowired
    private PurchaseQueryPort purchaseQueryPort;
    @Autowired
    private PurchaseCommandPort purchaseCommandPort;
    @Autowired
    private PurchaseFactoryPort purchaseFactoryPort;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Before
    public void setUp() {
        purchaseRepository.deleteAll();
    }

    @Test
    public void shouldFindNoOrder() {
        Long numberOfOrders = purchaseRepository.count();
        assertThat(numberOfOrders).isZero();
    }

    @Test
    @Transactional
    public void shouldFindOneOrderWithStatusCreated() {
        //given
        CreatePurchaseCommand createPurchaseCommand = new CreatePurchaseCommand(TEST_NAME);
        //when
        PurchaseData orderVM = purchaseFactoryPort.createPurchase(createPurchaseCommand);
        //then
        Long numberOfOrders = purchaseRepository.count();
        assertThat(numberOfOrders).isOne();
        PurchaseData foundedOrder = purchaseQueryPort.findPurchase(orderVM.getId());
        assertThat(foundedOrder).isNotNull();
        assertThat(foundedOrder.getOrderStateName()).isEqualTo(PurchaseState.DRAFT.name());
    }

    @Test
    @Transactional
    public void shouldCreateNewItem() {
        //given
        CreatePurchaseCommand createPurchaseCommand = new CreatePurchaseCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item", 123.33, 10);
        //when
        PurchaseData createdOrderVM = purchaseFactoryPort.createPurchase(createPurchaseCommand);
        PurchaseData orderDetailsVM = purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        //then
        Long numberOfOrders = purchaseRepository.count();
        assertThat(numberOfOrders).isOne();
        assertThat(orderDetailsVM).isNotNull();
        assertThat(orderDetailsVM.getOrderStateName()).isEqualTo(PurchaseState.DRAFT.name());
        assertThat(orderDetailsVM.getOrderItemProjections().size()).isEqualTo(1);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void shouldThrowQuantityIAE() {
        //given
        CreatePurchaseCommand createPurchaseCommand = new CreatePurchaseCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item", 123.33, -1);
        //when
        PurchaseData createdOrderVM = purchaseFactoryPort.createPurchase(createPurchaseCommand);
        //then - throw IllegalArgumentException
        purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
    }

    @Test(expected = IllegalArgumentException.class)
    @Transactional
    public void shouldThrowMoneyIAE() {
        //given
        CreatePurchaseCommand createPurchaseCommand = new CreatePurchaseCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item", -5.34, 1);
        //when
        PurchaseData createdOrderVM = purchaseFactoryPort.createPurchase(createPurchaseCommand);
        //then - throw IllegalArgumentException
        purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
    }

    @Test
    @Transactional
    public void shouldDeleteOneItemFromOrder() {
        //given
        CreatePurchaseCommand createPurchaseCommand = new CreatePurchaseCommand(TEST_NAME);
        AddItemCommand addItemCommand = new AddItemCommand("test_item", 5.34, 1);
        PurchaseData createdOrderVM = purchaseFactoryPort.createPurchase(createPurchaseCommand);
        purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        purchaseCommandPort.addItem(createdOrderVM.getId(), addItemCommand);
        final var orderItemsBeforeDeletion = purchaseQueryPort.findPurchase(createdOrderVM.getId()).getOrderItemProjections();
        final var indexOfFirstItemToRemove = 0;
        final var indexOfSecondItemToRemove = 4;
        final var firstItemToRemove = orderItemsBeforeDeletion.get(indexOfFirstItemToRemove);
        final var secondItemToRemove = orderItemsBeforeDeletion.get(indexOfSecondItemToRemove);
        //when
        purchaseCommandPort.deletePurchaseItem(createdOrderVM.getId(), firstItemToRemove.getId());
        purchaseCommandPort.deletePurchaseItem(createdOrderVM.getId(), secondItemToRemove.getId());
        //then
        final var orderItemsAfterDeletion = purchaseQueryPort.findPurchase(createdOrderVM.getId()).getOrderItemProjections();
        assertThat(orderItemsBeforeDeletion.size()).isGreaterThan(orderItemsAfterDeletion.size());
        assertThat(orderItemsAfterDeletion.contains(firstItemToRemove)).isFalse();
        assertThat(orderItemsAfterDeletion.contains(secondItemToRemove)).isFalse();

    }
}
