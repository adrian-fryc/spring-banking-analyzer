package pl.mentor.banking.analyzer.service;

import org.springframework.stereotype.Service;
import pl.mentor.banking.analyzer.dto.SystemStatus;

@Service
public class StatusService {

    public SystemStatus getCurrentStatus() {
        return new SystemStatus("System is up and running", "1.0", 0);
    }
}
