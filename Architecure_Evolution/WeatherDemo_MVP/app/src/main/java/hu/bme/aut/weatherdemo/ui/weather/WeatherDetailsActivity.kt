package hu.bme.aut.weatherdemo.ui.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import hu.bme.aut.weatherdemo.R
import hu.bme.aut.weatherdemo.model.WeatherResult
import kotlinx.android.synthetic.main.activity_weather_details.*
import kotlinx.android.synthetic.main.city_row.*
import java.util.*

class WeatherDetailsActivity : AppCompatActivity(), WeatherScreen {
    private lateinit var cityName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather_details)

        cityName = intent.getStringExtra("CITY_NAME")!!
        tvCity.text = cityName
    }

    override fun onStart() {
        super.onStart()
        WeatherPresenter.attachScreen(this)
    }

    override fun onStop() {
        WeatherPresenter.detachScreen()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        WeatherPresenter.queryWeatherDetails(cityName)
    }

    override fun showWeatherDetails(weatherData: WeatherResult) {
        val icon = weatherData?.weather?.get(0)?.icon
        Glide.with(this@WeatherDetailsActivity)
            .load("https://openweathermap.org/img/w/$icon.png")
            .into(ivWeatherIcon)

        tvMain.text = weatherData?.weather?.get(0)?.main
        tvDescription.text = weatherData?.weather?.get(0)?.description
        tvTemperature.text =
            getString(R.string.temperature, weatherData?.main?.temp?.toFloat().toString())

        val sdf = java.text.SimpleDateFormat("h:mm a z", Locale.getDefault())
        val sunriseDate = Date((weatherData?.sys?.sunrise?.toLong())!! * 1000)
        val sunriseTime = sdf.format(sunriseDate)
        tvSunrise.text = getString(R.string.sunrise, sunriseTime)
        val sunsetDate = Date(weatherData.sys.sunset?.toLong()!! * 1000)
        val sunsetTime = sdf.format(sunsetDate)
        tvSunset.text = getString(R.string.sunset, sunsetTime)
    }

    override fun showError(errorMsg: String) {
        tvCity.text = errorMsg
    }
}