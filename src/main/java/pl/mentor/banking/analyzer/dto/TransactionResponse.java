package pl.mentor.banking.analyzer.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TransactionResponse(String id, BigDecimal amount, String currency, LocalDate date, String category, String username) {
}
