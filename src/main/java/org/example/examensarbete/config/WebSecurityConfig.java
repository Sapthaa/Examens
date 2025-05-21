package org.example.examensarbete.config;

import org.example.examensarbete.services.CustomUserDetailsService;
import org.example.examensarbete.services.LoggerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private final LoggerService logger;

    public WebSecurityConfig(LoggerService logger) {
        this.logger = logger;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomUserDetailsService customUserDetailsService) {
        logger.info("Konfigurerar SecurityFilterChain...");

        try {
            http
                    .authorizeHttpRequests(authorize -> authorize
                            .requestMatchers("/", "/index", "/all-books", "/search-book", "/auth/login", "/auth/logout", "/auth/register", "/book-detail/**", "/styles.css", "/images/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer
                            .loginPage("/auth/login")
                            .defaultSuccessUrl("/", true)
                            .failureUrl("/auth/login?error=true")
                            .permitAll()
                    )
                    .logout(logout -> logout
                            .logoutSuccessUrl("/")
                            .permitAll()
                    )
                    .csrf(csrf -> csrf
                            .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                    )
                    .rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer
                            .rememberMeParameter("remember-me")
                            .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(1000))
                            .key("SecurityKey")
                            .userDetailsService(customUserDetailsService));

            logger.info("SecurityFilterChain konfigurerad utan fel.");
            return http.build();

        } catch (Exception e) {
            logger.error("Ett fel uppstod vid konfiguration av SecurityFilterChain: {}" + e.getMessage(), e);
            throw new RuntimeException("Kunde inte konfigurera säkerhetsinställningarna", e);
        }
    }
}
