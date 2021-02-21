package hu.bme.aut.weatherdemo.ui.weather

import hu.bme.aut.weatherdemo.model.City
import hu.bme.aut.weatherdemo.model.WeatherResult

interface WeatherScreen {
    fun showWeatherDetails(weatherData: WeatherResult)
    fun showError(errorMsg: String)
}