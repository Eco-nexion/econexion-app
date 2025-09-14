package io.econexion.lab.weather;

import io.econexion.lab.weather.dto.*;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WeatherService {
    private final Map<String, WeatherDto> reports = new ConcurrentHashMap<>();

    public WeatherGetResponse get(String locationId) {
        LocationDto loc = new LocationDto("Bogotá", "Colombia", "Cundinamarca");
        WeatherDto w = reports.getOrDefault(locationId.toLowerCase(),
                new WeatherDto(17.5, 994.71, 61));
        return new WeatherGetResponse(loc, w);
    }

    public void report(String locationId, WeatherPostRequest req) {
        reports.put(locationId.toLowerCase(), req.weather());
    }
}
