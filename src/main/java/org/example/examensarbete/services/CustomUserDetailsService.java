package org.example.examensarbete.services;
import org.example.examensarbete.Model.CustomUser;
import org.example.examensarbete.repositories.UserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final LoggerService logger;
    private final UserRepository userRepository;

    public CustomUserDetailsService(LoggerService logger, UserRepository userRepository) {
        this.logger = logger;
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            CustomUser user = userRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Användare hittades inte: " + username));

            logger.info("Användare hittad: {}" + user.getUsername());

            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .build();
        } catch (UsernameNotFoundException e) {

            logger.error("Fel uppstod vid inläsning av användare med användarnamn: {}" + username, e);
            throw e;
        } catch (Exception e) {

            logger.error("Oförutsett fel uppstod vid inläsning av användare med användarnamn: {}" + username, e);
            throw new RuntimeException("Oförutsett fel uppstod", e);
        }
    }
}
