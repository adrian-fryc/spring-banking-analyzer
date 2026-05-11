package pl.mentor.banking.analyzer.dto;

import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTransactionRequest(BigDecimal amount, String currency, LocalDate date, TransactionCategory category) {

}
