package algo.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@ComponentScan(basePackages ="algo.backend")
@EnableJpaRepositories(basePackages = "algo.backend")
@Configuration
public class SetupConfig {
    @Bean
    CommandLineRunner commandLineRunnerSetup(){
        return args -> {

        };
    }
}
