package pl.mentor.banking.analyzer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import pl.mentor.banking.analyzer.dto.CreateTransactionRequest;
import pl.mentor.banking.analyzer.dto.TransactionResponse;
import pl.mentor.banking.analyzer.exception.CategoryNotFoundException;
import pl.mentor.banking.analyzer.exception.UserNotFoundException;
import pl.mentor.banking.analyzer.model.AppUser;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;
import pl.mentor.banking.analyzer.repository.TransactionRepository;
import pl.mentor.banking.analyzer.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService {
//    private final List<Transaction> transactions;

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionService(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public List<Transaction> getTransactions() {
        return transactionRepository.findAll();
    }

    public List<TransactionResponse> getUserTransactions(String username) {
        return transactionRepository.findAllByUser_Username(username)
                .stream()
                .map(t -> new TransactionResponse(t.getId(), t.getAmount(), t.getCurrency(), t.getDate(), t.getCategory().name(), t.getUser().getUsername()))
                .toList();
//        return transactionRepository.findAllByUser_Username(username);
    }

    public BigDecimal calculateTotalSpent(TransactionCategory category){
        log.info("Calculating total spent for category: {}", category);
        List<Transaction> allTransactions = transactionRepository.findAll(); // Pobierz raz!
        boolean categoryExists = allTransactions.stream()
                .anyMatch(t -> t.getCategory().equals(category));

        if (!categoryExists) {
            throw new CategoryNotFoundException("Nie znaleziono transakcji dla kategorii: " + category);
        }

        return allTransactions.stream()
                .filter(t-> t.getCategory().equals(category))
                .map(Transaction::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addTransaction(CreateTransactionRequest dto, String username) {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + username));

        Transaction transaction = new Transaction(
                UUID.randomUUID().toString(),
                dto.amount(),
                dto.currency(),
                dto.date(),
                user, // Przypisujemy właściciela
                dto.category()
        );

        transactionRepository.save(transaction);
    }
}
