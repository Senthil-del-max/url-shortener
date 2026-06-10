package com.URL.urlshortner.service;

import com.URL.urlshortner.entity.UrlMapping;
import com.URL.urlshortner.repository.UrlRepository;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UrlService {

    private final UrlRepository repository;

    public UrlService(UrlRepository repository) {
        this.repository = repository;
    }

    public String createShortUrl(
            String originalUrl,
            String customAlias) {

        String shortCode;

        if (customAlias != null &&
                !customAlias.isEmpty()) {

            shortCode = customAlias;

        } else {

            shortCode = generateCode();
        }

        UrlMapping mapping = new UrlMapping();

        mapping.setOriginalUrl(originalUrl);
        mapping.setShortCode(shortCode);
        mapping.setClickCount(0);

        repository.save(mapping);

        return shortCode;
    }

    private String generateCode() {

        String chars =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        Random random = new Random();

        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }

        return sb.toString();
    }
}