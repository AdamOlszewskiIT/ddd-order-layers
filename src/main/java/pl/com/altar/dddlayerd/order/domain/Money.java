package pl.com.altar.dddlayerd.order.domain;

import lombok.Value;
import lombok.val;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

@Value
@Embeddable
class Money implements Serializable {

    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");

    public static final Money ZERO = new Money(BigDecimal.ZERO, DEFAULT_CURRENCY.getCurrencyCode());

    private final BigDecimal money;

    private final String currencyCode;

    public Money(double value, Currency currency) {
        this(new BigDecimal(value), currency.getCurrencyCode());
    }

    public Money(double value) {
        this(new BigDecimal(value), DEFAULT_CURRENCY.getCurrencyCode());
    }

    public Money(BigDecimal money, String currencyCode) {
        if (money.doubleValue() < 0) {
            throw new IllegalArgumentException("Value cannot be lower than 0");
        }
        this.money = money;
        this.currencyCode = currencyCode;
    }

    public Money mult(double multiplier) {
        val result = this.money.multiply(new BigDecimal(multiplier));
        return new Money(result, this.currencyCode);
    }

    public Money add(Money value) {
        val result = this.money.add(value.money);
        return new Money(result, this.currencyCode);
    }

    public Money sub(Money value) {
        val result = this.money.subtract(value.money);
        return new Money(result, this.currencyCode);
    }



    @Override
    public String toString() {
        return String.format("%0$.2f %s", money, getCurrencyCode());
    }
}
