package io.econexion.lab.weather;

import io.econexion.lab.weather.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/weather")
public class WeatherController {

    private final WeatherService service;

    public WeatherController(WeatherService service) {
        this.service = service;
    }

    @GetMapping("/{locationId}")
    public WeatherGetResponse get(@PathVariable String locationId) {
        return service.get(locationId);
    }

    @PostMapping("/{locationId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void report(@PathVariable String locationId,
                       @RequestBody @Valid WeatherPostRequest body) {
        service.report(locationId, body);
    }
}
