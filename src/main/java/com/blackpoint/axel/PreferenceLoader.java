package com.blackpoint.axel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.prefs.Preferences;

public class PreferenceLoader {
    private final Preferences preferences = Preferences.userNodeForPackage(PreferenceLoader.class);

    public PreferenceLoader(String path) throws IOException {
        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String token = br.readLine();

        preferences.put("token", token);
    }

    public String getToken() {
        return preferences.get("token", null);
    }
}
