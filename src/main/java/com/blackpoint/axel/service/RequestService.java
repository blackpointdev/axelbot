package com.blackpoint.axel.service;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

public class RequestService {
    private final RestTemplate restTemplate;

    public RequestService() {
        restTemplate = new RestTemplate();
    }

    public String getRequest(String url) {
        try {
            return restTemplate.getForEntity(url, String.class).getBody();
        }
        catch(HttpClientErrorException | NullPointerException e) {
            return null;
        }
    }
}
