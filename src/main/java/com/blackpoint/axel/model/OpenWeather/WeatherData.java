package com.blackpoint.axel.model.OpenWeather;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherData {
    int id;
    private String main;
    private String description;
    private String icon;

    private WeatherData() {}

    public WeatherData(int id, String main, String description, String icon) {
        this.id = id;
        this.main = main;
        this.description = description;
        this.icon = icon;
    }
}
