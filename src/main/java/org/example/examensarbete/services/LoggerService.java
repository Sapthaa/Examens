package org.example.examensarbete.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggerService {
    private final Logger logger = LoggerFactory.getLogger(LoggerService.class);

    public void info(String message) {
        logger.info(message);
    }

    public void warn(String message) {
        logger.warn(message);
    }

    public void error(String message, Exception e) {
        logger.error(message, e);
    }
}
