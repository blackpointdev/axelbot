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
            return "Aktualna temperatura w mieście " + city + " wynosi: " +
                    Float.toString(openWeatherData.getMainWeatherData().getTemp());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
