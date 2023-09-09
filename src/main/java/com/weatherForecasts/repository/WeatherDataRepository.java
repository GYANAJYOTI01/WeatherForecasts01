package com.weatherForecasts.repository;

import com.weatherForecasts.entity.WeatherData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeatherDataRepository extends JpaRepository<WeatherData, Long> {
    Optional<WeatherData> findByCityAndCountry(String city, String country);
}

