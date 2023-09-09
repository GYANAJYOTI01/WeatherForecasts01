package com.weatherForecasts.controller;

import com.weatherForecasts.entity.WeatherData;
import com.weatherForecasts.repository.WeatherDataRepository;
import com.weatherForecasts.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/weather")
public class WeatherController {
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private WeatherDataRepository weatherDataRepository;
    //http://localhost:8080/weather/city/country

    @GetMapping("/{city}/{country}")
    public ResponseEntity<WeatherData> getWeather(@PathVariable String city, @PathVariable String country) {
        // Check if weather data is available in the database

        Optional<WeatherData> weatherData = weatherDataRepository.findByCityAndCountry(city, country);

        if (weatherData.isPresent()) {
            return ResponseEntity.ok(weatherData.get());
        } else {
            try {
                // Fetch data from OpenWeatherMap API asynchronously
                CompletableFuture<WeatherData> futureWeather = weatherService.getWeatherData(city, country);
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            } catch (Exception e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        }
    }
}

