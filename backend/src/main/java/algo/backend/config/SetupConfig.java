package algo.backend.config;

import algo.backend.model.entity.User;
import algo.backend.model.entity.UserRole;
import algo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.Timestamp;
import java.util.Date;

@ComponentScan(basePackages = "algo.backend")
@EnableJpaRepositories(basePackages = "algo.backend")
@Configuration
@RequiredArgsConstructor
public class SetupConfig {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    CommandLineRunner commandLineRunnerSetup(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("andrzej").isEmpty()) {
                User user = new User(
                        "andrzej",
                        "def@gmail.com",
                        bCryptPasswordEncoder.encode("password123"), // Zaszyfrowane hasło
                        UserRole.ROLE_USER,
                        new Timestamp(new Date().getTime())
                );
                user.setEnabled(true);
                user.setLocked(false);
                userRepository.save(user);
                System.out.println("✅ Utworzono użytkownika testowego: andrzej / password123");
            }
        };
    }
}