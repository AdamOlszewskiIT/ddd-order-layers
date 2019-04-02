package pl.com.altar.ecommerce.shared;

import lombok.Value;
import pl.com.altar.ecommerce.shared.annotations.domain.ValueObject;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Currency;

@Value
@Embeddable
@ValueObject
public class Money implements Serializable {
    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");
    private BigDecimal denomination;
    private String currencyCode;
    @SuppressWarnings("unused")
    /* JPA Purpose only*/
    public Money() {
        this(0,DEFAULT_CURRENCY);
    }
    public Money(BigDecimal denomination, Currency currency) {
        this(denomination, currency.getCurrencyCode());
    }
    private Money(BigDecimal denomination, String currencyCode) {
        this.denomination = denomination.setScale(2, RoundingMode.HALF_EVEN);
        this.currencyCode = currencyCode;
    }
    public Money(BigDecimal denomination) {
        this(denomination, DEFAULT_CURRENCY);
        if(denomination.doubleValue() < 0) throw new IllegalArgumentException("");
    }
    public Money(double denomination, Currency currency) {
        this(new BigDecimal(denomination), currency.getCurrencyCode());
    }
    public Money(double denomination, String currencyCode) {
        this(new BigDecimal(denomination), currencyCode);
    }
    public Money(double denomination) {
        this(denomination, DEFAULT_CURRENCY);
        if(denomination < 0) throw new IllegalArgumentException("");
    }

    public Money multiplyBy(double multiplier) {
        return multiplyBy(parseDoubleToBC(multiplier));
    }
    public Money multiplyBy(BigDecimal multiplier) {
        return new Money(denomination.multiply(multiplier), currencyCode);
    }
    public Money add(Money money) {
        if (!compatibleCurrency(money)) {
            throw new IllegalArgumentException("Currency mismatch");
        }

        return new Money(denomination.add(money.denomination), determineCurrencyCode(money));
    }
    public Money subtract(Money money) {
        if (!compatibleCurrency(money))
            throw new IllegalArgumentException("Currency mismatch");

        return new Money(denomination.subtract(money.denomination), determineCurrencyCode(money));
    }
    public Currency getCurrency() {
        return Currency.getInstance(currencyCode);
    }
    public boolean greaterThan(Money other) {
        return denomination.compareTo(other.denomination) > 0;
    }
    public boolean lessThan(Money other) {
        return denomination.compareTo(other.denomination) < 0;
    }
    public boolean lessOrEquals(Money other) {
        return denomination.compareTo(other.denomination) <= 0;
    }
    public static Money zero() {
        return new Money(0);
    }

    private boolean compatibleCurrency(Money money) {
        return isZero(denomination) || isZero(money.denomination) || currencyCode.equals(money.getCurrencyCode());
    }
    private boolean isZero(BigDecimal testedValue) {
        return BigDecimal.ZERO.compareTo(testedValue) == 0;
    }
    private Currency determineCurrencyCode(Money otherMoney) {
        String resultingCurrentCode = isZero(denomination) ? otherMoney.currencyCode : currencyCode;
        return Currency.getInstance(resultingCurrentCode);
    }
    private BigDecimal parseDoubleToBC(double value) {
        return new BigDecimal(String.valueOf(value));
    }
    @Override
    public String toString() {
        return String.format("%0$.2f %s", denomination, getCurrency().getSymbol());
    }
}
