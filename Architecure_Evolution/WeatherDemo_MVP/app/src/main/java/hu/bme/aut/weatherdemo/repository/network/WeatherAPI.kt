package hu.bme.aut.weatherdemo.repository.network

import hu.bme.aut.weatherdemo.model.WeatherResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {
    @GET("/data/2.5/weather")
    fun getWeatherData(@Query("q") cityName: String,
                       @Query("units") units: String,
                       @Query("appid") appId: String): Call<WeatherResult>
}