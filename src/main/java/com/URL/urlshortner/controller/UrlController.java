package com.URL.urlshortner.controller;

import com.URL.urlshortner.dto.UrlRequest;
import com.URL.urlshortner.dto.UrlResponse;
import com.URL.urlshortner.entity.UrlMapping;
import com.URL.urlshortner.repository.UrlRepository;
import com.URL.urlshortner.service.UrlService;
import com.URL.urlshortner.util.QrCodeGenerator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Optional;

@RestController
@CrossOrigin("*")
public class UrlController {

    private final UrlService service;
    private final UrlRepository repository;

    public UrlController(
            UrlService service,
            UrlRepository repository) {

        this.service = service;
        this.repository = repository;
    }

    @PostMapping("/shorten")
    public UrlResponse shorten(
            @RequestBody UrlRequest request) {

        String code = service.createShortUrl(
                request.getUrl(),
                request.getCustomAlias()
        );

        // CHANGE THIS TO YOUR RENDER URL
        String baseUrl = "https://url-shortener-2itn.onrender.com";

        String shortUrl = baseUrl + "/s/" + code;

        String qrCode = QrCodeGenerator.generateQRCode(shortUrl);

        return new UrlResponse(
                shortUrl,
                qrCode
        );
    }

    @GetMapping("/s/{shortCode}")
    public ResponseEntity<?> redirect(
            @PathVariable String shortCode) {

        Optional<UrlMapping> optional =
                repository.findByShortCode(shortCode);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        UrlMapping mapping = optional.get();

        mapping.setClickCount(
                mapping.getClickCount() + 1
        );

        repository.save(mapping);

        return ResponseEntity.status(302)
                .location(
                        URI.create(
                                mapping.getOriginalUrl()
                        )
                )
                .build();
    }

    @GetMapping("/analytics/{shortCode}")
    public ResponseEntity<?> analytics(
            @PathVariable String shortCode) {

        Optional<UrlMapping> optional =
                repository.findByShortCode(shortCode);

        if (optional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(
                optional.get()
        );
    }
}