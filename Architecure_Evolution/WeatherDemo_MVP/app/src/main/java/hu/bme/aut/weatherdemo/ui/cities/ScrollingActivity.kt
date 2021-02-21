package hu.bme.aut.weatherdemo.ui.cities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import hu.bme.aut.weatherdemo.R
import hu.bme.aut.weatherdemo.ui.cities.adapter.CityAdapter
import hu.bme.aut.weatherdemo.repository.database.AppDatabase
import hu.bme.aut.weatherdemo.model.City
import kotlinx.android.synthetic.main.activity_scrolling.*

class ScrollingActivity : AppCompatActivity(), CitiesScreen {

    lateinit var cityAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scrolling)
        setSupportActionBar(toolbar)
        fab.setOnClickListener { view ->
            showAddCityDialog()
        }
    }

    override fun onStart() {
        super.onStart()
        CitiesPresenter.attachScreen(this)
    }

    override fun onStop() {
        CitiesPresenter.detachScreen()
        super.onStop()
    }

    override fun onResume() {
        super.onResume()
        initRecyclerView()
        CitiesPresenter.queryCities(this)
    }

    private fun initRecyclerView() {
        cityAdapter = CityAdapter(this)
        listCities.adapter = cityAdapter
    }

    private fun showAddCityDialog() {

        MaterialDialog(this).show {
            noAutoDismiss()
            title(text = "Add City Name")
            input()

            positiveButton(text="Add") {
                val cityName = it.getInputField().text.toString()
                if (cityName.isNotEmpty()) {
                    saveCity(City(null, cityName))
                    it.dismiss()
                } else {
                    it.getInputField().error = "Required"
                }
            }
            negativeButton(text="Dismiss") {
                it.dismiss()
            }
        }

    }


    fun saveCity(newCity: City) {
        Thread {
            newCity.cityId = AppDatabase.getInstance(this).cityDao().insertCity(newCity)

            runOnUiThread {
                cityAdapter.addCity(newCity)
            }
        }.start()

    }

    override fun showCities(citiesList: List<City>) {
        cityAdapter.setCities(citiesList)
    }
}
