package org.example.examensarbete.repositories;

import jakarta.annotation.PostConstruct;
import org.example.examensarbete.Model.CustomUser;
import org.example.examensarbete.services.LoggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class TestUserInit {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final LoggerService logger;

    @Autowired
    public TestUserInit(UserRepository userRepository, PasswordEncoder passwordEncoder, LoggerService logger) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.logger = logger;
    }

    @PostConstruct
    public void createUser() {
        try {
            if (userRepository.count() == 0) {
                logger.info("No users found in the database. Creating an admin user...");

                CustomUser user = new CustomUser();
                user.setUsername("test");
                user.setPassword(passwordEncoder.encode("test"));

                userRepository.save(user);
                logger.info("User created successfully.");
            } else {
                logger.info("User already exist in the database. Skipping user creation.");
            }
        } catch (Exception e) {
            logger.error("Error occurred while initializing the database: {}" + e.getMessage(), e);
        }
    }
}
