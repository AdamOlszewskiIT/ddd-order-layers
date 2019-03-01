package pl.com.altar.dddlayerd.client;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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
import pl.com.altar.dddlayerd.client.OrderCommandController;
import pl.com.altar.dddlayerd.client.command.AddItemCommand;
import pl.com.altar.dddlayerd.client.command.CreateOrderCommand;
import pl.com.altar.dddlayerd.client.vm.OrderVM;
import pl.com.altar.dddlayerd.domain.order.ports.OrderCommandPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderFactoryPort;
import pl.com.altar.dddlayerd.domain.order.ports.OrderQueryPort;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderCommandControllerTest {

    private static final String TEST_NAME = "test_name";
    private static final String DRAFT = "DRAFT";

    @Autowired
    private OrderCommandPort orderCommandPort;

    @Autowired
    private OrderFactoryPort orderFactoryPort;

    @Autowired
    private OrderQueryPort orderQueryPort;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc mockMvc;

    private ObjectWriter objectWriter;

    @Before
    public void setup() {
        OrderCommandController ctrl = new OrderCommandController(orderCommandPort, orderFactoryPort);
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
        orderCommandPort.deleteAll();
    }

    @Test
    public void createOrder() throws Exception {
        CreateOrderCommand command = new CreateOrderCommand(TEST_NAME);
        String requestJSON = objectWriter.writeValueAsString(command);
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(is(TEST_NAME)))
                .andExpect(jsonPath("$.orderState").value(is(DRAFT)));
        final var orderList = orderQueryPort.findAll();
        assertThat(orderList.size()).isEqualTo(1);
    }

    @Test
    @Transactional
    public void createOrderAndAddOneItem() throws Exception {
        CreateOrderCommand command = new CreateOrderCommand(TEST_NAME);
        AddItemCommand itemCommand = new AddItemCommand(TEST_NAME, 13.26D, 12);
        String requestJSON = objectWriter.writeValueAsString(command);
        mockMvc.perform(post("/orders")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJSON));
        List<OrderVM> orderList = orderQueryPort.findAll();
        OrderVM order = orderList.get(0);
        requestJSON = objectWriter.writeValueAsString(itemCommand);
        mockMvc.perform(post("/orders/{orderId}/items", order.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(requestJSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
        final var orderDetails = orderQueryPort.findOrder(order.getId());
        assertThat(orderDetails.getOrderItemVMList().size()).isEqualTo(1);
    }


}
