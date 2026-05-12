package pl.mentor.banking.analyzer.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mentor.banking.analyzer.dto.CreateTransactionRequest;
import pl.mentor.banking.analyzer.exception.CategoryNotFoundException;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;
import pl.mentor.banking.analyzer.repository.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository; // Udajemy repozytorium

    @InjectMocks
    private TransactionService transactionService; // Spring wstrzyknie mocka do serwisu

    private List<Transaction> testData;

    @BeforeEach
    void setUp() {
        // Musimy "opakować" List.of w coś modyfikowalnego, np. ArrayList lub CopyOnWriteArrayList
        testData = (List.of(
                new Transaction("1", BigDecimal.valueOf(100), "PLN", LocalDate.now(), TransactionCategory.FOOD),
                new Transaction("2", BigDecimal.valueOf(200), "PLN", LocalDate.now(), TransactionCategory.FUEL),
                new Transaction("3", BigDecimal.valueOf(300), "PLN", LocalDate.now(), TransactionCategory.SALARY),
                new Transaction("4", BigDecimal.valueOf(400), "PLN", LocalDate.now(), TransactionCategory.RENT),
                new Transaction("5", BigDecimal.valueOf(500), "PLN", LocalDate.now(), TransactionCategory.FOOD)
        ));

    }

    @Test
    void shouldCalculateTotalSpentForCategory(){
        // 2. PROGRAMUJEMY MOCKA (STUBBING)
        // Mówimy: "Drogi mocku, gdy serwis wywoła findAll(), udawaj że w bazie jest to:"
        org.mockito.Mockito.when(repository.findAll()).thenReturn(testData);
        BigDecimal totalSpent = transactionService.calculateTotalSpent(TransactionCategory.FOOD);
        assertEquals(BigDecimal.valueOf(600), totalSpent);
        assertThat(totalSpent).isEqualByComparingTo("600");
    }

    @Test
    void shouldThrowExceptionWhenCategoryNotFound(){
        // Tu też musimy zaprogramować mocka, inaczej zwróci pustą listę i rzuci błąd (ale z innego powodu)
        org.mockito.Mockito.when(repository.findAll()).thenReturn(testData);
        assertThrows(CategoryNotFoundException.class, () -> transactionService.calculateTotalSpent(TransactionCategory.SHOPPING));
    }

    @Test
    void shouldReturnEmptyListWhenNoTransactionsExist(){
        List<Transaction> transactions = transactionService.getTransactions();
        assertEquals(0, transactions.size());
        assertThat(transactions).isEmpty();
        assertThrows(CategoryNotFoundException.class, () -> transactionService.calculateTotalSpent(TransactionCategory.SHOPPING));
    }

    @Test
    void shouldAddNewTransaction(){
        // 1. Przygotuj DTO (bez ID!)
        CreateTransactionRequest request = new CreateTransactionRequest(
                new BigDecimal("150.00"),
                "PLN",
                LocalDate.now(),
                TransactionCategory.FOOD
        );
        // 2. Wywołaj metodę
        transactionService.addTransaction(request);

        // 3. WERYFIKACJA (To jest klucz w Mockito!)
        // Nie sprawdzamy rozmiaru listy (bo listy już nie ma w serwisie!),
        // sprawdzamy czy serwis wywołał metodę .save() na repozytorium.
        org.mockito.Mockito.verify(repository).save(org.mockito.Mockito.any(Transaction.class));

//        // 3. Sprawdź czy lista urosła i czy dane się zgadzają
//        List<Transaction> allTransactions = transactionService.getTransactions();
//
//        assertThat(allTransactions).hasSize(6); // Było 5 w setUp + 1 nowa
//        assertThat(allTransactions)
//                .extracting(Transaction::getAmount)
//                .contains(new BigDecimal("150.00"));
    }
}
