package org.example.examensarbete.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @GetMapping("")
    public String dashboard(Authentication authentication, Model model) {
        // Hämta användardata från Authentication-objektet
        String username = authentication.getName();
        model.addAttribute("username", username);

        // Här skulle du kunna hämta mer användardata från din UserService
        // t.ex. antal böcker, läslistor etc.

        return "dashboard";
    }

    @GetMapping("/books")
    public String myBooks(Model model) {
        // Hämta användarens böcker från en BookService
        // model.addAttribute("books", bookService.getUserBooks(username));
        return "dashboard/books";
    }

    @GetMapping("/lists")
    public String myLists(Model model) {
        // Hämta användarens läslistor
        return "dashboard/lists";
    }
}