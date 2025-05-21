package org.example.examensarbete;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ExamensArbeteApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamensArbeteApplication.class, args);
    }

}
