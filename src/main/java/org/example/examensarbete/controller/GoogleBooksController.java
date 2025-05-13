package org.example.examensarbete.controller;

import org.example.examensarbete.services.GoogleBooksService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class GoogleBooksController {

    @Autowired
    private GoogleBooksService googleBooksService;

    @GetMapping("/{id}")
    public String getBook(@PathVariable String id) {
        return googleBooksService.searchBookViaId(id);
    }
}
