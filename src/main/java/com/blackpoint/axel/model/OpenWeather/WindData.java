package com.blackpoint.axel.model.OpenWeather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WindData {
    private float speed;
    private int deg;

    public WindData() { }

    public WindData(float speed, int deg) {
        this.speed = speed;
        this.deg = deg;
    }
}
