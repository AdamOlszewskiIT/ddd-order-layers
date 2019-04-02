package pl.com.altar.ecommerce.sales.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import pl.com.altar.ecommerce.sales.client.command.AddItemCommand;
import pl.com.altar.ecommerce.sales.client.command.CreatePurchaseCommand;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseCommandPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseFactoryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.ports.PurchaseQueryPort;
import pl.com.altar.ecommerce.sales.domain.purchase.projections.PurchaseData;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseCommandControllerTest {

    private static final String TEST_NAME = "test_name";
    private static final String DRAFT = "DRAFT";

    @Autowired
    private PurchaseCommandPort purchaseCommandPort;

    @Autowired
    private PurchaseFactoryPort purchaseFactoryPort;

    @Autowired
    private PurchaseQueryPort purchaseQueryPort;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;

    private ObjectWriter objectWriter;

    @Before
    public void setUp() {
        OrderCommandController ctrl = new OrderCommandController(purchaseCommandPort, purchaseFactoryPort);
        this.mockMvc = MockMvcBuilders.standaloneSetup(ctrl)
                .setCustomArgumentResolvers(pageableArgumentResolver)
                .build();
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        mapper.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule())
                .enable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES)
                .registerModule(new ParameterNamesModule(JsonCreator.Mode.PROPERTIES));
        objectWriter = mapper.writer().withDefaultPrettyPrinter();
        purchaseCommandPort.deleteAll();
    }

    @After
    public void clear() {
        purchaseCommandPort.deleteAll();
    }

    @Test
    public void createOrder() throws Exception {
        CreatePurchaseCommand command = new CreatePurchaseCommand(TEST_NAME);
        String requestJSON = objectWriter.writeValueAsString(command);
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(is(TEST_NAME)))
                .andExpect(jsonPath("$.orderState").value(is(DRAFT)));
        final var orderList = purchaseQueryPort.findAll();
        assertThat(orderList.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void createOrderAndAddOneItem() throws Exception {
        CreatePurchaseCommand command = new CreatePurchaseCommand(TEST_NAME);
        AddItemCommand itemCommand = new AddItemCommand(TEST_NAME, 13.26D, 12);
        String requestJSON = objectWriter.writeValueAsString(command);
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJSON));
        List<PurchaseData> orderList = purchaseQueryPort.findAll();
        PurchaseData order = orderList.get(0);
        requestJSON = objectWriter.writeValueAsString(itemCommand);
        mockMvc.perform(post("/orders/{orderId}/items", order.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        final var orderDetails = purchaseQueryPort.findPurchase(order.getId());
        assertThat(orderDetails.getOrderItemProjections().size()).isEqualTo(1);
    }


}
