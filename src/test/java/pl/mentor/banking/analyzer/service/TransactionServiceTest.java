package pl.mentor.banking.analyzer.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.mentor.banking.analyzer.dto.CreateTransactionRequest;
import pl.mentor.banking.analyzer.exception.CategoryNotFoundException;
import pl.mentor.banking.analyzer.model.AppUser;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;
import pl.mentor.banking.analyzer.repository.TransactionRepository;
import pl.mentor.banking.analyzer.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepository repository; // Udajemy repozytorium

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransactionService transactionService; // Spring wstrzyknie mocka do serwisu

    private List<Transaction> testData;
    private AppUser testUser;

    @BeforeEach
    void setUp() {
        testUser = new AppUser();
        testUser.setUsername("admin");
        // Musimy "opakować" List.of w coś modyfikowalnego, np. ArrayList lub CopyOnWriteArrayList
        testData = (List.of(
                new Transaction("1", BigDecimal.valueOf(100), "PLN", LocalDate.now(), testUser, TransactionCategory.FOOD),
                new Transaction("2", BigDecimal.valueOf(200), "PLN", LocalDate.now(), testUser, TransactionCategory.FUEL),
                new Transaction("3", BigDecimal.valueOf(300), "PLN", LocalDate.now(), testUser, TransactionCategory.SALARY),
                new Transaction("4", BigDecimal.valueOf(400), "PLN", LocalDate.now(), testUser, TransactionCategory.RENT),
                new Transaction("5", BigDecimal.valueOf(500), "PLN", LocalDate.now(), testUser, TransactionCategory.FOOD)
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
    void shouldAddNewTransaction() {
        // GIVEN
        CreateTransactionRequest request = new CreateTransactionRequest(
                new BigDecimal("150.00"), "PLN", LocalDate.now(), TransactionCategory.FOOD
        );
        // Mówimy Mockowi: "Kiedy ktoś spyta o usera 'admin', oddaj naszego testUsera"
        Mockito.when(userRepository.findByUsername("admin")).thenReturn(Optional.of(testUser));

        // WHEN
        transactionService.addTransaction(request, "admin");

        // THEN
        Mockito.verify(repository).save(any(Transaction.class));
    }
}
