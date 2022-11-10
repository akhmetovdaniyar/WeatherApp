package ru.akhmetovdaniyar.weatherapp.data.models

data class ModelWeather(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)