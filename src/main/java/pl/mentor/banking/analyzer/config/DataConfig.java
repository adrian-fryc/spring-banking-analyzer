package pl.mentor.banking.analyzer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataConfig {

    @Bean
    public List<Transaction> transactions() {
        return List.of(
            new Transaction("1", BigDecimal.valueOf(100), "PLN", LocalDate.now(), TransactionCategory.FOOD),
            new Transaction("2", BigDecimal.valueOf(200), "PLN", LocalDate.now(), TransactionCategory.FUEL),
            new Transaction("3", BigDecimal.valueOf(300), "PLN", LocalDate.now(), TransactionCategory.SALARY),
            new Transaction("4", BigDecimal.valueOf(400), "PLN", LocalDate.now(), TransactionCategory.RENT),
            new Transaction("5", BigDecimal.valueOf(500), "PLN", LocalDate.now(), TransactionCategory.FOOD)
        );
    }
}
