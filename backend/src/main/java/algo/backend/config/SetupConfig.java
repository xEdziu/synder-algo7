package algo.backend.config;

import algo.backend.model.entity.User;
import algo.backend.model.entity.UserRole;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Date;

@ComponentScan(basePackages ="algo.backend")
@EnableJpaRepositories(basePackages = "algo.backend")
@Configuration
public class SetupConfig {
    @Bean
    CommandLineRunner commandLineRunnerSetup(){
        return args -> {
            User user = new User(
                    "andrzej",
                    "def@gmail.com",
                    "hashedpassword",
                    UserRole.ROLE_USER,
                    new java.sql.Timestamp(new Date().getTime())
            );
        };
    }
}
