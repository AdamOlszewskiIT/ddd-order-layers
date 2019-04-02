package pl.com.altar.ecommerce.sales.client.command;

import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.io.Serializable;



@Value
@Validated
@AllArgsConstructor
public class CreatePurchaseCommand implements Serializable {
    @NotNull
    private String name;

    @SuppressWarnings("unused")
    /* Jackson serialization purpose only */
    public CreatePurchaseCommand() {
        this("default");
    }
}
