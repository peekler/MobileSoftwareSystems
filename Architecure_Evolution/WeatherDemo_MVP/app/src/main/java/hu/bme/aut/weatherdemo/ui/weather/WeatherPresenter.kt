package hu.bme.aut.weatherdemo.ui.weather

import hu.bme.aut.weatherdemo.model.WeatherResult
import hu.bme.aut.weatherdemo.repository.network.WeatherAPI
import hu.bme.aut.weatherdemo.ui.Presenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherPresenter : Presenter<WeatherScreen?>() {

    fun queryWeatherDetails(cityName: String) {
        var retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherAPI::class.java)

        val weatherCall =
            weatherApi.getWeatherData(
                cityName,
                "metric",
                "f3d694bc3e1d44c1ed5a97bd1120e8fe"
            )

        weatherCall.enqueue(object : Callback<WeatherResult> {
            override fun onFailure(call: Call<WeatherResult>, t: Throwable) {
                screen?.showError(t.message!!)
            }

            override fun onResponse(call: Call<WeatherResult>, response: Response<WeatherResult>) {
                val weatherData = response.body()
                screen?.showWeatherDetails(weatherData!!)
            }
        })
    }

}
