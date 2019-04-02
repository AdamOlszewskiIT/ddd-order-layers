package pl.com.altar.ecommerce.sales.infrastructure.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.com.altar.ecommerce.sales.domain.equivalent.SuggestionService;

@Configuration
public class EquivalentConfiguration {

    @Bean
    public SuggestionService getSuggestionService() {
        return new SuggestionService();
    }

}
