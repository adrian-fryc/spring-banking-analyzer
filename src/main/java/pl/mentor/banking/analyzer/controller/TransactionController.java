package pl.mentor.banking.analyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mentor.banking.analyzer.model.Transaction;
import pl.mentor.banking.analyzer.service.TransactionService;

import java.util.List;

@RestController
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/api/transactions")
    public List<Transaction> getTransactions() {
        return transactionService.transactions;
    }
}
