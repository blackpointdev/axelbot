package com.blackpoint.axel.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

@Service
public class CredentialsService {

    public HashMap<String, String> getTokenFromEnvVar(String... name) {
        HashMap<String, String> tokens = new HashMap<>();

        for(String n : name) {
            String t = System.getenv(n);
            if (t != null) {
                tokens.put(n, System.getenv(n));
            }
        }

        if (tokens.isEmpty()) return null;

        return tokens;
    }

    public HashMap<String, String> getTokenFromFile(String path, String... name) throws IOException {
        PropertyLoaderService propertyLoaderService = new PropertyLoaderService();
        Properties properties = propertyLoaderService.getProperties(path);

        if (properties.isEmpty()) {
            return null;
        }

        HashMap<String, String> tokens = new HashMap<>();
        for (String n : name) {
            tokens.put(n, properties.getProperty(n));
        }
        return tokens;
    }
}
