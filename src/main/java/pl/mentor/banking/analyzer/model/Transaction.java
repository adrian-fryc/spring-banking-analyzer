package pl.mentor.banking.analyzer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "transactions")
@Getter
@Setter
@ToString
@NoArgsConstructor // JPA tego WYMAGA (pusty konstruktor)
@AllArgsConstructor // Do tworzenia obiektów w testach
public class Transaction {
    @Id
    private String id;
    private BigDecimal amount;
    private String currency;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TransactionCategory category;
}


//public record Transaction(String id, BigDecimal amount, String currency, LocalDate date, TransactionCategory category) {
//    //    Metoda dodana w celu treningu - nie używam póki co
//    public void isNegative(){
//        if(amount.compareTo(BigDecimal.ZERO) < 0){
//            throw new IllegalArgumentException("Amount cannot be negative");
//        }
//    }
//}