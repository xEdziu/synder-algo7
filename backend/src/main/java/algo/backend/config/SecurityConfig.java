package algo.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                    .requestMatchers("/api/v1/authorized/admin/**").hasAuthority("ROLE_ADMIN")
                    .requestMatchers("/api/v1/authorized/**").hasAnyAuthority("ROLE_USER", "ROLE_ADMIN")
                    .requestMatchers("/api/v1/**").permitAll()
                    .requestMatchers("/**", "/static/**", "/resources/**").permitAll()
                    .requestMatchers("/styles/**", "/scripts/**", "/img/**", "/fonts/**").permitAll()
                    .requestMatchers("/actuator/health").permitAll() // Allow access to health check
                    .requestMatchers("/styles/**", "/scripts/**", "/img/**", "/fonts/**").permitAll()
                    .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
}