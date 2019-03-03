package pl.com.altar.ecommerce.sales.application.shared;

import org.junit.Test;
import pl.com.altar.ecommerce.shared.ExceptionUtil;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ExceptionUtilTest {

    @Test
    public void shouldThrowException() {
        //given
        Object nullObject = null;
        final var option = Optional.ofNullable(nullObject);
        //when //then
        assertThatExceptionOfType(RuntimeException.class).isThrownBy(()-> ExceptionUtil.handleOption(option, new RuntimeException("Null")));
    }

    @Test
    public void shouldReturnObject() {
        //given
        Object nonNullObject = new Object();
        final var option = Optional.of(nonNullObject);
        //when
        final var result = ExceptionUtil.handleOption(option, new RuntimeException("Null"));
        // then
        assertThat(nonNullObject).isEqualTo(result);
    }

}
