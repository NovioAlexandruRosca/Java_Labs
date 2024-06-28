package com.example.restservice.config;

import java.io.InputStream;
import java.util.Properties;

public class Config {
    private final Properties properties = new Properties();

    public Config() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }
            properties.load(input);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}