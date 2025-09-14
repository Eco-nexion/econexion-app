package io.econexion.lab.weather.dto;

import jakarta.validation.constraints.NotNull;

public record WeatherPostRequest(@NotNull WeatherDto weather) { }
