package com.blackpoint.axel.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.prefs.Preferences;

@Service
public class PropertyLoaderService {

    public Properties getProperties(String path) throws IOException {
        Properties properties = new Properties();
        properties.load(new FileReader(path));

        return properties;
    }
}
