package org.example.examensarbete.controller;

import jakarta.validation.Valid;
import org.example.examensarbete.Model.CustomUser;
import org.example.examensarbete.services.LoggerService;
import org.example.examensarbete.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final LoggerService logger;

    @Autowired
    private UserService userService;

    public AuthController(LoggerService logger) {
        this.logger = logger;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        logger.info("Visar registreringsformuläret");

        try {
            model.addAttribute("customUser", new CustomUser());
            return "auth/register";
        } catch (Exception e) {
            logger.error("Ett fel uppstod vid laddning av registreringssidan: {}", e.getMessage(), e);
            model.addAttribute("error", "Kunde inte ladda registreringssidan.");
            return "error";
        }
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("customUser") CustomUser customUser, BindingResult bindingResult, Model model) {
        logger.info("Försöker registrera användare: {}", customUser.getUsername());

        try {
            if (bindingResult.hasErrors()) {
                logger.warn("Formulärfel vid registrering av användare: {}", customUser.getUsername());
                model.addAttribute("error", "Det finns fel i formuläret. Vänligen rätta till dem.");
                return "auth/register";
            }

            String result = userService.registerUser(customUser);
            if (result != null) {
                logger.warn("Registrering misslyckades för användare: {} - Felmeddelande: {}", customUser.getUsername(), result);
                model.addAttribute("error", result);
                return "auth/register";
            }

            logger.info("Registrering lyckades för användare: {}", customUser.getUsername());
            model.addAttribute("success", "Registreringen lyckades!");
            return "auth/register/success";

        } catch (Exception e) {
            logger.error("Ett fel uppstod vid registrering av användare: {} - Fel: {}", customUser.getUsername(), e.getMessage(), e);
            model.addAttribute("error", "Ett internt fel uppstod vid registrering.");
            return "error";
        }
    }

    @PostMapping("/delete/user")
    public String deleteUser(@PathVariable String username, Model model) {
        logger.info("Försöker ta bort användare: {}", username);

        try {
            String result = userService.deleteUser(username);
            if (result != null) {
                logger.warn("Misslyckades med att ta bort användare: {} - Felmeddelande: {}", username, result);
                model.addAttribute("error", result);
                return "auth/delete";
            }

            logger.info("Användaren {} har tagits bort", username);
            model.addAttribute("success", "Användaren har tagits bort.");
            return "index";

        } catch (Exception e) {
            logger.error("Ett fel uppstod vid borttagning av användare: {} - Fel: {}", username, e.getMessage(), e);
            model.addAttribute("error", "Ett internt fel uppstod vid borttagning.");
            return "error";
        }
    }

}
