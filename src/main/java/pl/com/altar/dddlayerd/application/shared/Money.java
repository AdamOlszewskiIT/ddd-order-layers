package pl.com.altar.dddlayerd.application.shared;

import lombok.Value;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

@Value
@Embeddable
public class Money implements Serializable {


    private static final Locale DEFAULT_LOCALE = Locale.getDefault();

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private static final NumberFormat format = NumberFormat.getCurrencyInstance(DEFAULT_LOCALE);

    private final BigDecimal money;

    public Money() {
        this(BigDecimal.ZERO);
    }

    public Money(double value) {
        this(new BigDecimal(value));
    }

    private Money(BigDecimal money) {
        if (money.doubleValue() < 0) {
            throw new IllegalArgumentException("Value cannot be lower than 0");
        }
        this.money = money;
    }

    public Money mult(double multiplier) {
        BigDecimal result = this.money.multiply(new BigDecimal(multiplier));
        return new Money(result);
    }

    public Money add(Money value) {
        BigDecimal result = this.money.add(value.money);
        return new Money(result);
    }

    public Money sub(Money value) {
        BigDecimal result = this.money.subtract(value.money);
        return new Money(result);
    }

    @Override
    public String toString() {
        return format.format(money);
    }
}
