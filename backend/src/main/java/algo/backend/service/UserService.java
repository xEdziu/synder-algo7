package algo.backend.service;

import algo.backend.model.dto.UserDTO;
import algo.backend.model.entity.User;
import algo.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.oauth2.jwt.Jwt;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika: " + username));

        return user;
    }
    public UserDTO getAuthorizedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();
        switch (principal) {
            case User foundUser -> {
                return new UserDTO(foundUser.getId(), foundUser.getUsername(), foundUser.getEmail());
            }
            case Jwt jwt -> {
                String username = jwt.getClaimAsString("sub");
                User userEntity = userRepository.findByUsername(username)
                        .orElseThrow(() -> new IllegalStateException("Nie znaleziono użytkownika: " + username));
                return new UserDTO(
                        userEntity.getId(),
                        userEntity.getUsername(),
                        userEntity.getEmail()
                );
            }
            case null, default -> throw new IllegalStateException("Nie udało się pobrać zalogowanego użytkownika.");
            }
        }
    }



}
