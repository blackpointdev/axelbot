package com.blackpoint.axel.service;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;

public class CredentialsService {

    @Value("${TELEGRAM_API_TOKEN}")
    private String telegramApiToken;
    @Value("${WEATHER_API_TOKEN}")
    private String weatherApiToken;

    public CredentialsService(String path) throws IOException {

        if (telegramApiToken == null || weatherApiToken == null) {
            PreferenceLoaderService preferenceLoaderService = new PreferenceLoaderService(path);

            telegramApiToken = preferenceLoaderService.getTelegramApiToken();
            weatherApiToken = preferenceLoaderService.getWeatherApiToken();
        }
    }

    public String getTelegramApiToken() {
        return telegramApiToken;
    }

    public String getWeatherApiToken() {
        return weatherApiToken;
    }
}
