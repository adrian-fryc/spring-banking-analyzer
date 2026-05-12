package pl.mentor.banking.analyzer.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.mentor.banking.analyzer.dto.CreateTransactionRequest;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.model.TransactionCategory;
import pl.mentor.banking.analyzer.service.TransactionService;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/api/transactions")
    public List<Transaction> getTransactions() {
        return transactionService.getTransactions();
    }

    @GetMapping("/api/transactions/total")
        public BigDecimal calculateTotalSpent(@RequestParam TransactionCategory category) {
        return transactionService.calculateTotalSpent(category);
    }

    @PostMapping("/api/transactions")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTransaction(@Valid @RequestBody CreateTransactionRequest dtoTransaction) {
        log.info("Adding new transaction: {}", dtoTransaction);
        transactionService.addTransaction(dtoTransaction);
    }
}
