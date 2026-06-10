package com.URL.urlshortner.dto;

public class UrlResponse {

    private String shortUrl;
    private String qrCode;

    public UrlResponse(
            String shortUrl,
            String qrCode) {

        this.shortUrl = shortUrl;
        this.qrCode = qrCode;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public String getQrCode() {
        return qrCode;
    }
}