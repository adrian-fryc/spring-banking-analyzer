package pl.mentor.banking.analyzer.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.mentor.banking.analyzer.exception.CategoryNotFoundException;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionService emptyTransactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(
            List.of(
                new Transaction("1", BigDecimal.valueOf(100), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("2", BigDecimal.valueOf(200), "PLN", LocalDate.now(), TransactionCategory.FUEL),
                new Transaction("3", BigDecimal.valueOf(300), "PLN", LocalDate.now(), TransactionCategory.SALARY),
                new Transaction("4", BigDecimal.valueOf(400), "PLN", LocalDate.now(), TransactionCategory.RENT),
                new Transaction("5", BigDecimal.valueOf(500), "PLN", LocalDate.now(), TransactionCategory.FOOD)
        ));
        emptyTransactionService = new TransactionService(List.of());
    }

    @Test
    void shouldCalculateTotalSpentForCategory(){
        BigDecimal totalSpent = transactionService.calculateTotalSpent(TransactionCategory.FOOD);
        assertEquals(BigDecimal.valueOf(600), totalSpent);
        assertThat(totalSpent).isEqualByComparingTo("600");
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound(){
        assertThrows(CategoryNotFoundException.class, () -> transactionService.calculateTotalSpent(TransactionCategory.SHOPPING));
    }

    @Test
    void shouldReturnEmptyListWhenNoTransactionsExist(){
        List<Transaction> transactions = emptyTransactionService.getTransactions();
        assertEquals(0, transactions.size());
        assertThat(transactions).isEmpty();
        assertThrows(CategoryNotFoundException.class, () -> emptyTransactionService.calculateTotalSpent(TransactionCategory.SHOPPING));
    }
}
