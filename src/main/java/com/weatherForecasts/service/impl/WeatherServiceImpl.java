package com.weatherForecasts.service.impl;

import com.weatherForecasts.entity.WeatherData;
import com.weatherForecasts.repository.WeatherDataRepository;
import com.weatherForecasts.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherServiceImpl  implements WeatherService {
    @Autowired
    private WeatherDataRepository weatherDataRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Async
    public CompletableFuture<WeatherData> getWeatherData(String city, String country) {
        String url = apiUrl + "?q=" + city + "," + country + "&appid=" + apiKey;

        WeatherData weatherData = restTemplate.getForObject(url, WeatherData.class);

        if (weatherData != null) {
            weatherDataRepository.save(weatherData);
        }

        return CompletableFuture.completedFuture(weatherData);
    }
}

