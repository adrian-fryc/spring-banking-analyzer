package pl.mentor.banking.analyzer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.mentor.banking.analyzer.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, String> {
}
