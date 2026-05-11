package pl.mentor.banking.analyzer.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import pl.mentor.banking.analyzer.dto.CreateTransactionRequest;
import pl.mentor.banking.analyzer.exception.CategoryNotFoundException;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class TransactionService {
    private final List<Transaction> transactions;

    public TransactionService(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public BigDecimal calculateTotalSpent(TransactionCategory category){
        log.info("Calculating total spent for category: {}", category);
        boolean categoryExists = transactions.stream()
                .anyMatch(t -> t.category().equals(category));

        if (!categoryExists) {
            throw new CategoryNotFoundException("Nie znaleziono transakcji dla kategorii: " + category);
        }

        return transactions.stream()
                .filter(t-> t.category().equals(category))
                .map(Transaction::amount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void addTransaction(CreateTransactionRequest dtoTransaction){
        log.info("Adding new transaction: {}", dtoTransaction);
        Transaction transaction = new Transaction(UUID.randomUUID().toString(), dtoTransaction.amount(), dtoTransaction.currency(), dtoTransaction.date(), dtoTransaction.category());
        transactions.add(transaction);
    }
}
