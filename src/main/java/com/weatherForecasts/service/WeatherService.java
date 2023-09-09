package com.weatherForecasts.service;

import com.weatherForecasts.entity.WeatherData;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {
    CompletableFuture<WeatherData> getWeatherData(String city, String country);
}
