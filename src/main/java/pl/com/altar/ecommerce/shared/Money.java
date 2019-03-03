package pl.com.altar.ecommerce.shared;

import lombok.Value;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;
import java.util.Objects;

@Value
@Embeddable
public class Money implements Serializable {


    public static final Currency DEFAULT_CURRENCY = Currency.getInstance("EUR");

    public static final Money ZERO = new Money(0);

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
        return multiplyBy(new BigDecimal(multiplier));
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

    /**
     * Currency is compatible if the same or either money object has zero value.
     */
    private boolean compatibleCurrency(Money money) {
        return isZero(denomination) || isZero(money.denomination) || currencyCode.equals(money.getCurrencyCode());
    }

    private boolean isZero(BigDecimal testedValue) {
        return BigDecimal.ZERO.compareTo(testedValue) == 0;
    }

    /**
     * @return currency from this object or otherCurrencyCode. Preferred is the
     *         one that comes from Money that has non-zero value.
     */
    private Currency determineCurrencyCode(Money otherMoney) {
        String resultingCurrenctCode = isZero(denomination) ? otherMoney.currencyCode : currencyCode;
        return Currency.getInstance(resultingCurrenctCode);
    }

    public String getCurrencyCode() {
        return currencyCode;
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

    public static Money ZERO() {
        return new Money(0);
    }

    @Override
    public String toString() {
        return String.format("%0$.2f %s", denomination, getCurrency().getSymbol());
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((currencyCode == null) ? 0 : currencyCode.hashCode());
        result = prime * result + ((denomination == null) ? 0 : denomination.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Money other = (Money) obj;
        return compatibleCurrency(other) && Objects.equals(denomination, other.denomination);
    }
}
