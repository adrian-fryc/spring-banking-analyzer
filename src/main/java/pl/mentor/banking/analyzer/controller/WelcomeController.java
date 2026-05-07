package pl.mentor.banking.analyzer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mentor.banking.analyzer.dto.SystemStatus;
import pl.mentor.banking.analyzer.service.StatusService;

@RestController
public class WelcomeController {

    private final StatusService statusService;

    public WelcomeController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("/api/hello")
    public String sayHello() {
        return "Witaj w maju! Spring Boot melduje gotowość.";
    }

    @GetMapping("/api/status")
    public SystemStatus getStatus(){
        return statusService.getCurrentStatus();
    }
}
