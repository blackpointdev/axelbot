package com.blackpoint.axel.service;

import java.io.*;
import java.util.prefs.Preferences;

public class PreferenceLoaderService {
    private final Preferences preferences = Preferences.userNodeForPackage(PreferenceLoaderService.class);

    public PreferenceLoaderService(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new FileNotFoundException();
        }
        BufferedReader br = new BufferedReader(new FileReader(file));

        String telegramApiToken = br.readLine();
        preferences.put("TELEGRAM_API_TOKEN", telegramApiToken);
        String weatherApiToken = br.readLine();
        preferences.put("WEATHER_API_TOKEN", weatherApiToken);
    }

    public String getTelegramApiToken() {
        return preferences.get("TELEGRAM_API_TOKEN", null);
    }
    public String getWeatherApiToken() {return preferences.get("WEATHER_API_TOKEN", null); }
}
