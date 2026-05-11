package pl.mentor.banking.analyzer.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.validation.annotation.Validated;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Configuration
@Validated
public class DataConfig {
    @Value("${app.transactions.file-name}")
    private String transactionsFileName;

    @Bean
    public List<Transaction> transactions(ObjectMapper objectMapper) throws IOException {
        log.info("Loading transactions from file: {}", transactionsFileName);
        if (transactionsFileName == null) {
            throw new IllegalArgumentException("Transactions file name is not specified");
        }
        InputStream inputStream = new ClassPathResource(transactionsFileName).getInputStream();
        // 1. Wczytujemy dane do tymczasowej listy
        List<Transaction> initialData = objectMapper.readValue(inputStream, new TypeReference<List<Transaction>>() {});

        // 2. Zwracamy listę bezpieczną wątkowo, wypełnioną tymi danymi
        return new CopyOnWriteArrayList<>(initialData);
    }

}

//    @Bean
//    public List<Transaction> transactions(ObjectMapper objectMapper) throws IOException {
//        InputStream inputStream = new ClassPathResource("transactions.json").getInputStream();
//        return objectMapper.readValue(inputStream, new TypeReference<List<Transaction>>() {});
//    }

//    @Bean
//    public List<Transaction> transactions()
//    {
//        return List.of(
//            new Transaction("1", BigDecimal.valueOf(100), "PLN", LocalDate.now(), TransactionCategory.FOOD),
//            new Transaction("2", BigDecimal.valueOf(200), "PLN", LocalDate.now(), TransactionCategory.FUEL),
//            new Transaction("3", BigDecimal.valueOf(300), "PLN", LocalDate.now(), TransactionCategory.SALARY),
//            new Transaction("4", BigDecimal.valueOf(400), "PLN", LocalDate.now(), TransactionCategory.RENT),
//            new Transaction("5", BigDecimal.valueOf(500), "PLN", LocalDate.now(), TransactionCategory.FOOD)
//        );
//    }
