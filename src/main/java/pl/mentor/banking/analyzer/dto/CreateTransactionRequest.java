package pl.mentor.banking.analyzer.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import pl.mentor.banking.analyzer.model.TransactionCategory;

import java.math.BigDecimal;
import java.time.LocalDate;


public record CreateTransactionRequest(
        @NotNull(message = "Amount cannot be null")
        @Positive(message = "Amount must be greater than zero")
        BigDecimal amount,

        @NotBlank(message = "Currency is required")
        @Size(min = 3, max = 3, message = "Currency must be 3 characters long (e.g., PLN)")
        String currency,

        @NotNull(message = "Date is required")
        LocalDate date,

        @NotNull(message = "Category is required")
        TransactionCategory category) {
}
