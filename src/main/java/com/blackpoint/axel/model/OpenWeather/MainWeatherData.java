package com.blackpoint.axel.model.OpenWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MainWeatherData {
    private float temp;
    @JsonProperty("feels_like")
    private float feelsLike;
    @JsonProperty("temp_min")
    private float tempMin;
    @JsonProperty("temp_max")
    private float tempMax;
    private float pressure;
    private float humidity;

    public MainWeatherData() {}

    public MainWeatherData(float temp, float feelsLike, float tempMin, float tempMax, float pressure, float humidity) {
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.tempMin = tempMin;
        this.tempMax = tempMax;
        this.pressure = pressure;
        this.humidity = humidity;
    }
}
