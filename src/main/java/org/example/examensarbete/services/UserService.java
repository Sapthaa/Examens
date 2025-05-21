package org.example.examensarbete.services;

import org.example.examensarbete.Model.CustomUser;
import org.example.examensarbete.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggerService logger;


    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggerService logger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
    }

    public String registerUser(CustomUser customUser) {
        try {
            if (userRepository.findByUsername(customUser.getUsername()).isPresent()) {
                logger.warn("Försök att registrera en användare som redan finns: {}" + customUser.getUsername());
                return "Användaren finns redan.";
            }

            String encodedPassword = passwordEncoder.encode(customUser.getPassword());
            customUser.setPassword(encodedPassword);

            userRepository.save(customUser);
            logger.info("Ny användare registrerad: {}" + customUser.getUsername());
            return "Registrering utfördes";

        } catch (Exception e) {
            logger.error("Fel vid registrering av användare: {}" + e.getMessage(), e);
            return "Fel vid registrering av användare.";
        }
    }

    public String deleteUser(String username) {
        try {
            CustomUser user = userRepository.findByUsername(username).orElse(null);
            if (user == null) {
                logger.warn("Försök att ta bort en icke-existerande användare: {}" + username);
                return "Användaren finns inte.";
            }

            userRepository.delete(user);
            logger.info("Användare borttagen: {}" + username);
            return "Användaren har tagits bort.";

        } catch (Exception e) {
            logger.error("Fel vid borttagning av användare: {}" + e.getMessage(), e);
            return "Fel vid borttagning av användare.";
        }
    }

}
