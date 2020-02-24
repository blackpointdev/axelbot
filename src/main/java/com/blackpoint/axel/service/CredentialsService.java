package com.blackpoint.axel.service;

import java.io.IOException;

public class CredentialsService {

    private String telegramApiToken;
    private String weatherApiToken;

    public CredentialsService(String path) throws IOException {
        telegramApiToken = System.getenv("TELEGRAM_API_TOKEN");
        weatherApiToken = System.getenv("WEATHER_API_TOKEN");
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
