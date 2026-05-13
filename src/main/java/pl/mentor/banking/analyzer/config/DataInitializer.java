package pl.mentor.banking.analyzer.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.mentor.banking.analyzer.model.AppUser;
import pl.mentor.banking.analyzer.repository.UserRepository;
import pl.mentor.banking.analyzer.security.CustomUserDetailsService;

@Slf4j
@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        // Sprawdzamy bezpośrednio w bazie przez repozytorium
        if (userRepository.findByUsername("admin").isEmpty()) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");

            userRepository.save(admin);
            log.info("Admin user created");
        }else{
            log.info("Admin user already exists");
        }

        if (userRepository.findByUsername("user").isEmpty()) {
            AppUser user = new AppUser();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRole("USER");

            userRepository.save(user);
            log.info("USER user created");
        }else{
            log.info("USER user already exists");
        }
    }
}
