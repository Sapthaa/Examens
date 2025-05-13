package org.example.examensarbete.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GoogleBooksService {

    private final WebClient webClient;

    @Value("${api.key}")
    private String apiKey;

    public GoogleBooksService(WebClient.Builder webClient) {
        this.webClient = webClient.baseUrl("https://www.googleapis.com/books/v1/volumes").build();
    }

    public String searchBookViaId(String id) {
        return webClient.get().uri("/{id}?key={key}", id, apiKey)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
