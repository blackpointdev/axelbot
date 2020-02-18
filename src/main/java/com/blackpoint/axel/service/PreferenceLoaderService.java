package com.blackpoint.axel.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.prefs.Preferences;

public class PreferenceLoaderService {
    private final Preferences preferences = Preferences.userNodeForPackage(PreferenceLoaderService.class);

    public PreferenceLoaderService(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String telegramApiToken = br.readLine();
        preferences.put("telegramApiToken", telegramApiToken);
        String weatherApiToken = br.readLine();
        preferences.put("weatherApiToken", weatherApiToken);
    }

    public String telegramApiToken() {
        return preferences.get("telegramApiToken", null);
    }
    public String getWeatherApiToken() {return preferences.get("weatherApiToken", null); }
}
