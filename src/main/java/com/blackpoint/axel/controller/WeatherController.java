package com.blackpoint.axel.controller;

import com.blackpoint.axel.model.OpenWeather.OpenWeatherData;
import com.blackpoint.axel.service.RequestService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class WeatherController {
    private String weatherApiToken;

    public WeatherController(String weatherApiToken) {
        this.weatherApiToken = weatherApiToken;
    }

    public String getWeather(String city) {
        RequestService requestService = new RequestService();
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + weatherApiToken +
                "&units=metric";
        String json = requestService.getRequest(url);

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            OpenWeatherData openWeatherData = objectMapper.readValue(json, OpenWeatherData.class);
            return(
                    "Current weather data for: <b><i>" + city + "</i></b>\n" +
                    "Temperature: <b>" + openWeatherData.getMainWeatherData().getTemp() + "\u00B0C </b>\n" +
                            "Feels like temperature: <b> " + openWeatherData.getMainWeatherData().getFeelsLike() + "\u00B0C </b>\n" +
                            "Conditions: <b>" + openWeatherData.getWeatherData().getMain() + ", " + openWeatherData.getWeatherData().getDescription() + "</b>\n" +
                            "Wind speed: <b>" + openWeatherData.getWindData().getSpeed() + " m/s </b>\n" +
                            "Wind direction: <b>" + openWeatherData.getWindData().getDeg() + "\u00B0 </b>\n"
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        catch (IllegalArgumentException e) {
            return "City " + city + " was not found.";
        }
    }
}
