package hu.bme.aut.weatherdemo.ui.cities

import hu.bme.aut.weatherdemo.model.City

interface CitiesScreen {
    fun showCities(citiesList: List<City>)
}
