package com.blackpoint.axel.model.OpenWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherData {
    @JsonProperty("coord")
    private Coordinates coordinates;
    @JsonProperty("weather")
    private WeatherData[] weatherData;
    private String base;
    @JsonProperty("main")
    private MainWeatherData mainWeatherData;
    private int visibility;
    @JsonProperty("wind")
    private WindData windData;
    @JsonProperty("clouds")
    private CloudsData cloudsData;
    private int dt;
    @JsonProperty("sys")
    SystemData systemData;
    private int timezone;
    private int id;
    private String name;
    private int cod;



    public OpenWeatherData() {}

    public OpenWeatherData(Coordinates coordinates,
                           WeatherData[] weatherData,
                           String base,
                           MainWeatherData mainWeatherData,
                           int visibility, WindData windData,
                           CloudsData cloudsData, int dt,
                           SystemData systemData, int timezone,
                           int id,
                           String name,
                           int cod) {
        this.coordinates = coordinates;
        this.weatherData = weatherData;
        this.base = base;
        this.mainWeatherData = mainWeatherData;
        this.visibility = visibility;
        this.windData = windData;
        this.cloudsData = cloudsData;
        this.dt = dt;
        this.systemData = systemData;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }
}
