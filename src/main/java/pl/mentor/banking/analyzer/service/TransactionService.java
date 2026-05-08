package pl.mentor.banking.analyzer.service;

import org.springframework.stereotype.Service;
import pl.mentor.banking.analyzer.exception.CategoryNotFoundException;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
}
