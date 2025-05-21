package org.example.examensarbete.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerService {
    private final Logger logger = LoggerFactory.getLogger(LoggerService.class);

    // String message = loggar endast ett meddelande
    // Object... args = för att kunna använda parameter i loggning{} (parameterized logging)
    // Throwable t = loggar hela felet(stacktrace)

    public void info(String message) {
        logger.info(message);
    }

    public void info(String message, Object... args) {
        logger.info(message, args);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void warn(String message, Object... args) {
        logger.warn(message, args);
    }

    public void error(String message) {
        logger.error(message);
    }

    public void error(String message, Object... args) {
        logger.error(message, args);
    }

    public void error(String message, Throwable t) {
        logger.error(message, t);
    }
}
